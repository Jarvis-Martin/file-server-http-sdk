package exception;

import lombok.Data;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-16:24
 * @version: 1.0
 */

/**
 * 服务器端业务异常类
 */
@Data
public class BussnissException extends RuntimeException{
    private Integer errorCode;


    public BussnissException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
