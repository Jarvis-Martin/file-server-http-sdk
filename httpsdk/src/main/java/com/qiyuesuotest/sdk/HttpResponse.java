package com.qiyuesuotest.sdk;

import lombok.Builder;

import java.io.InputStream;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/17-7:48
 * @version: 1.0
 */
@Builder
public class HttpResponse {
    /**
     * 响应码
     */
    private int code;
    /**
     * 响应内容长度
     */
    private long contentLength;
    /**
     * 响应体: 从 HttpURLConnection 中读取到的 byte 数据
     */
    private byte[] body;
    /**
     * 保存错误信息
     */
    private Exception exception;

    public byte[] body() {
        return body;
    }

    public void body(byte[] body) {
        this.body = body;
    }

    public int code() {
        return code;
    }

    public void code(int code) {
        this.code = code;
    }

    public long contentLength() {
        return contentLength;
    }

    public void contentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public Exception exception() {
        return exception;
    }

    public void exception(Exception exception) {
        this.exception = exception;
    }
}
