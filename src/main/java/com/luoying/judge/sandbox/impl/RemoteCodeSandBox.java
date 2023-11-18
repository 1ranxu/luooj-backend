package com.luoying.judge.sandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.luoying.common.ErrorCode;
import com.luoying.exception.BusinessException;
import com.luoying.judge.sandbox.CodeSandBox;
import com.luoying.judge.sandbox.model.ExecuteCodeRequest;
import com.luoying.judge.sandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;

/**
 * 远程代码沙箱（真正调用了我们开发的代码沙箱接口，代码沙箱不在本地实现，而是使用docker）
 */
public class RemoteCodeSandBox implements CodeSandBox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);
        String url = "http://localhost:8500/executeCode";
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(jsonStr)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandBox error message = " + responseStr);
        }
        System.out.println(responseStr);

        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
