package response;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-9:22
 * @version: 1.0
 */

/**
 * 服务器端自定义 响应对象
 */
public class ResponseDTO implements Serializable {
    /**
     * 响应码: 恒为 200，代表服务器成功接收到了客户端请求，并成功处理完成
     */
    private final Integer responseCode = 200;
    /**
     * 错误码: 对应的错误码，
     */
    private Integer errorCode;
    /**
     * 错误信息: 错误信息
     */
    private String errorMessage;
    /**
     * 数据区
     */
    private Object data;

    public ResponseDTO(Integer errorCode, String errorMessage, Object data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
