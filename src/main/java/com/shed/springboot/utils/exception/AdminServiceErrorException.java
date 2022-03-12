package com.shed.springboot.utils.exception;

/**
 * 与管理员业务相关的异常
 */
public class AdminServiceErrorException extends Exception {
    public AdminServiceErrorException(String message) {
        super(message);
    }
}
