package com.catchtabling.common.exception.customex;

import org.springframework.core.NestedRuntimeException;

public class AlreadyReservedException extends NestedRuntimeException {
    private final ErrorCode errorCode;

    public AlreadyReservedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AlreadyReservedException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AlreadyReservedException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
