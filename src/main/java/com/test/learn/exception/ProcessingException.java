package com.test.learn.exception;

import com.test.learn.util.enums.ErrorCode;

/**
 * Responsible for exceptions occurred while doing the processing of request
 */
public class ProcessingException extends APIException {

    public ProcessingException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProcessingException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
