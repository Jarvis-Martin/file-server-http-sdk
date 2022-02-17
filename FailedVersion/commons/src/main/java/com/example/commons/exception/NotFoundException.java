package com.example.commons.exception;

import java.util.Optional;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/15-16:24
 * @version: 1.0
 */
public class NotFoundException extends Exception{
    private Integer statusCode;


    public NotFoundException() {
        super("404 Not Found");
        this.statusCode = 404;
    }
    public NotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }
}
