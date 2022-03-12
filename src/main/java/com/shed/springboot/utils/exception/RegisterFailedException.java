package com.shed.springboot.utils.exception;

/**
 * 与注册操作相关的异常
 */
public class RegisterFailedException extends Exception {
    public RegisterFailedException(String message) {
        super(message);
    }
}
