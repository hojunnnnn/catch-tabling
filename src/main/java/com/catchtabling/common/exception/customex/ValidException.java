package com.catchtabling.common.exception.customex;

public class ValidException extends CatchTablingException {

    protected ValidException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ValidException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
