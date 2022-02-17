package com.qiyuesuotest.server.util.exception;

import exception.BussnissException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import response.ResponseDTO;

import java.util.HashMap;

/**
 * @Author: Coder_Jarvis
 * @Description:
 * @Date: 2021-10-2021/10/24-22:54
 * @Version: 1.0
 */

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BussnissException.class)
    @ResponseBody
    public ResponseDTO AuthenticateFailExceptionHandler(BussnissException e) {
        return new ResponseDTO(
                e.getErrorCode(),
                e.getMessage(),
                new HashMap<>(1));
    }

    // TODO 为了调试查看错误信息方便先注释掉，记得改回来哈
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseDTO ExceptionHandler(Exception e) {
//        return new ResponseDTO(
//                "500",
//                e.getMessage(),
//                e.getMessage(),
//                JSONUtils.fromJson("{}", Object.class));
//    }
}
