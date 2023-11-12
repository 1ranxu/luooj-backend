package com.luoying.judge.sandbox.impl;

import com.luoying.judge.sandbox.CodeSandBox;
import com.luoying.judge.sandbox.model.ExecuteCodeRequest;
import com.luoying.judge.sandbox.model.ExecuteCodeResponse;
import com.luoying.model.dto.questionsubmit.QuestionSubmitJudgeInfo;
import com.luoying.model.enums.JudgeInfoMessagenum;
import com.luoying.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        QuestionSubmitJudgeInfo judgeInfo = new QuestionSubmitJudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessagenum.ACCEPTED.getText());
        judgeInfo.setMemory(1000l);
        judgeInfo.setTime(1000l);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
