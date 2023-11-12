package com.luoying.judge.strategy;

import com.luoying.judge.strategy.impl.DefaultJudgeStrategy;
import com.luoying.judge.strategy.impl.JavaJudgeStrategy;
import com.luoying.model.dto.questionsubmit.QuestionSubmitJudgeInfo;
import com.luoying.model.entity.Question;
import com.luoying.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化代码）
 */
@Service
public class JudgeManager {

    public QuestionSubmitJudgeInfo doJudge(JudgeContext context) {
        QuestionSubmit questionSubmit = context.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(context);
    }
}
