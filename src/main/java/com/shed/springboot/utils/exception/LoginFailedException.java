package com.shed.springboot.utils.exception;

/**
 * 与登录操作相关的异常
 */
public class LoginFailedException extends Exception {
    public LoginFailedException(String message) {
        super(message);
    }
}
