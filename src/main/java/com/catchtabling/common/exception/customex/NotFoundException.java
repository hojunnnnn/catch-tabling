package com.catchtabling.common.exception.customex;

public class NotFoundException extends CatchTablingException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
