package com.test.learn.exception;


import com.test.learn.util.enums.ErrorCode;

/**
 * Responsible for exceptions while reading user input
 */
public class InvalidRequestException extends APIException {

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRequestException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
