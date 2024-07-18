package com.catchtabling.common.exception.customex;

public class AlreadyReservedException extends CatchTablingException {

    public AlreadyReservedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
