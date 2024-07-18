package com.catchtabling.common.exception.customex;

import org.springframework.core.NestedRuntimeException;

public abstract class CatchTablingException extends NestedRuntimeException {
    private final ErrorCode errorCode;

    protected CatchTablingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected CatchTablingException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected CatchTablingException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
