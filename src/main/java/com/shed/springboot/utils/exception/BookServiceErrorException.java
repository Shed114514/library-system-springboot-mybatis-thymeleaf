package com.shed.springboot.utils.exception;

/**
 * 与图书业务相关的异常
 */
public class BookServiceErrorException extends Exception {
    public BookServiceErrorException(String message) {
        super(message);
    }
}
