package com.test.learn.exception;


import com.test.learn.util.enums.ErrorCode;

public abstract class APIException extends RuntimeException {

    APIException(ErrorCode errorCode) {
        super(errorCode.getValue());
    }

    APIException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode.getValue(), throwable);
    }
}
