package com.qiyuesuotest.server.domain;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-9:48
 * @version: 1.0
 */

/**
 * 响应信息枚举类
 */
public enum ResponseStatusEnum {

    /**
     * 成功处理时的错误码 和 错误信息
     */
    SUCCESS(200, "一切 ok"),
    /**
     * 用户请求参数错误时的错误码 和 错误信息
     */
    ERROR(410, "出现错误");

    /**
     * 错误码
     */
    private Integer errorCode;
    /**
     * 错误信息
     */
    private String errorMessage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ResponseStatusEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
