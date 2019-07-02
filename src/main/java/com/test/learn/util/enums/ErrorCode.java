package com.test.learn.util.enums;

public enum ErrorCode {
    ERR_API_000(Constants.GENERIC_ERROR),

    ERR_API_INP_001(Constants.EMPTY_REQUEST_OBJECT),
    ERR_API_INP_002(Constants.INVALID_SRC_STATION),
    ERR_API_INP_003(Constants.INVALID_DEST_STATION),
    ERR_API_INP_004(Constants.INVALID_STARTING_TIME);

    private final String errorDesc;

    ErrorCode(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public static String getErrorMsg(String value) {
        return valueOf(value).errorDesc;
    }

    public static ErrorCode getEnum(String value) {
        return valueOf(value);
    }

    public String getValue() {
        return this.name();
    }

    private static class Constants {
        private static final String GENERIC_ERROR = "Request Failed - Retry.";

        private static final String EMPTY_REQUEST_OBJECT = "Request object can not be empty or partial";
        private static final String INVALID_SRC_STATION = "Given sourceStation is invalid";
        private static final String INVALID_DEST_STATION = "Given destinationStation is invalid";
        private static final String INVALID_STARTING_TIME = "Given startingTime is invalid";

    }

}
