package com.luoying.judge;

import cn.hutool.json.JSONUtil;
import com.luoying.common.ErrorCode;
import com.luoying.exception.BusinessException;
import com.luoying.judge.sandbox.CodeSandBox;
import com.luoying.judge.sandbox.CodeSandBoxFactory;
import com.luoying.judge.sandbox.CodeSandBoxProxy;
import com.luoying.judge.sandbox.model.ExecuteCodeRequest;
import com.luoying.judge.sandbox.model.ExecuteCodeResponse;
import com.luoying.judge.strategy.JudgeContext;
import com.luoying.judge.strategy.JudgeManager;
import com.luoying.judge.strategy.JudgeStrategy;
import com.luoying.judge.strategy.impl.DefaultJudgeStrategy;
import com.luoying.judge.strategy.impl.JavaJudgeStrategy;
import com.luoying.model.dto.question.QuestionJudgeCase;
import com.luoying.model.dto.questionsubmit.QuestionSubmitJudgeInfo;
import com.luoying.model.entity.Question;
import com.luoying.model.entity.QuestionSubmit;
import com.luoying.model.enums.QuestionSubmitStatusEnum;
import com.luoying.model.vo.QuestionSubmitVO;
import com.luoying.service.QuestionService;
import com.luoying.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmitVO doJudge(long questionSubmitId) {
        // 1 传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存");
        }
        // 2 如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已在判题");
        }
        // 3 更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新失败");
        }
        // 4、调用沙箱，获取到执行结果
        String code = questionSubmit.getCode();
        // 获取输入用例
        List<QuestionJudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), QuestionJudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(QuestionJudgeCase::getInput).collect(Collectors.toList());

        String language = questionSubmit.getLanguage();
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(codeSandBox);
        ExecuteCodeRequest codeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.executeCode(codeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5 根据沙箱的执行结果，设置题目的判题状态和信息

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setOutputList(outputList);
        judgeContext.setInputList(inputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestionSubmit(questionSubmit);

        QuestionSubmitJudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改提交记录的判题状态和判题信息
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新失败");
        }
        return QuestionSubmitVO.objToVo(questionSubmitService.getById(questionSubmitId));
    }
}
