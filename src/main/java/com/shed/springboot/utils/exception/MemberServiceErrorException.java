package com.shed.springboot.utils.exception;

/**
 * 与用户业务相关的异常
 */
public class MemberServiceErrorException extends Exception {
    public MemberServiceErrorException(String message) {
        super(message);
    }
}
