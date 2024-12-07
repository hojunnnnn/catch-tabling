package com.catchtabling.common.dto;

public record ApiResponseFormat(
        String timestamp,
        int status,
        String error,
        String path,
        Object message
) {
}
