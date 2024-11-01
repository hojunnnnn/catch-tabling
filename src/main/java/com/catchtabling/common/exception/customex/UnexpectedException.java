package com.catchtabling.common.exception.customex;

public class UnexpectedException extends CatchTablingException {

    public UnexpectedException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}
