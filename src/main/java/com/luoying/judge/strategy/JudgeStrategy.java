package com.luoying.judge.strategy;

import com.luoying.model.dto.questionsubmit.QuestionSubmitJudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    QuestionSubmitJudgeInfo doJudge(JudgeContext judgeContext);
}
