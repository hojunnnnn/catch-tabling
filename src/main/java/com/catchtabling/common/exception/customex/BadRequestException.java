package com.catchtabling.common.exception.customex;

public class BadRequestException extends CatchTablingException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
