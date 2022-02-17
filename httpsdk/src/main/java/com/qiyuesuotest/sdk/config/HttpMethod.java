package com.qiyuesuotest.sdk.config;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-14:25
 * @version: 1.0
 */

/**
 * Http 方法，题目中 文件上传下载需要 GET、POST，其余方式暂时没有实现
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST");
    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String method() {
        return method;
    }

}
