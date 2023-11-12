package com.luoying.judge.strategy;

import com.luoying.model.dto.question.QuestionJudgeCase;
import com.luoying.model.dto.questionsubmit.QuestionSubmitJudgeInfo;
import com.luoying.model.entity.Question;
import com.luoying.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;
@Data
public class JudgeContext {

    private List<String> inputList;

    private List<String> outputList;

    private List<QuestionJudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmitJudgeInfo judgeInfo;

    private QuestionSubmit questionSubmit;
}
