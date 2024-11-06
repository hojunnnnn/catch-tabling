package com.catchtabling.common.exception.customex;

public class ReserveFailException extends CatchTablingException {
    public ReserveFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
