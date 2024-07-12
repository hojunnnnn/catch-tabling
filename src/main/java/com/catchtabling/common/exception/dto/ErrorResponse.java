package com.catchtabling.common.exception.dto;

import com.catchtabling.common.exception.customex.ErrorCode;

public record ErrorResponse(
        ErrorCode errorCode,
        String message
) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode, errorCode.getMessage());
    }
}
