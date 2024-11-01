package com.catchtabling.common.exception.customex;

public class ValidException extends CatchTablingException {

    protected ValidException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ValidException( String message) {
        super(ErrorCode.VALIDATION_FAIL, message);
    }
}
