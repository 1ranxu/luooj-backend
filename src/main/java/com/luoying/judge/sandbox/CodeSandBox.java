package com.luoying.judge.sandbox;

import com.luoying.judge.sandbox.model.ExecuteCodeRequest;
import com.luoying.judge.sandbox.model.ExecuteCodeResponse;

public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
