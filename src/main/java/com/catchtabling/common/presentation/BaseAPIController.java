package com.catchtabling.common.presentation;

import com.catchtabling.common.dto.ApiResponseFormat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class BaseAPIController {

    protected final HttpServletRequest httpServletRequest;

    public <T> ResponseEntity<ApiResponseFormat> responseEntityOk(@Nullable T body) {
        ApiResponseFormat responseFormat = new ApiResponseFormat(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                null,
                httpServletRequest.getRequestURI(),
                body
        );

        return ResponseEntity.ok(responseFormat);
    }

    public <T> ResponseEntity<ApiResponseFormat> responseEntityOkWithHttpStatus(HttpStatus httpStatus,
                                                                                @Nullable T body) {
        ApiResponseFormat responseFormat = new ApiResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                null,
                httpServletRequest.getRequestURI(),
                body
        );

        return new ResponseEntity<>(responseFormat, httpStatus);
    }
}
