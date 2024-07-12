package com.catchtabling.common.dto;

public record DefaultResponseFormat(
        String timestamp,
        int status,
        String error,
        String path,
        Object message
) {
}
