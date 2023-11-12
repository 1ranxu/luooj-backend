package com.luoying.judge;

import com.luoying.model.vo.QuestionSubmitVO;

/**
 * 怕媒体服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmitVO doJudge(long questionSubmitId);
}
