package com.luoying.judge.sandbox.impl;

import com.luoying.judge.sandbox.CodeSandBox;
import com.luoying.judge.sandbox.model.ExecuteCodeRequest;
import com.luoying.judge.sandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（真正调用了我们开发的代码沙箱接口，代码沙箱不在本地实现，而是使用docker）
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
