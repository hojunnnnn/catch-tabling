package com.catchtabling.common.presentation;

import com.catchtabling.common.dto.DefaultResponseFormat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class BaseAPIController {

    protected final HttpServletRequest httpServletRequest;

    public <T> ResponseEntity<DefaultResponseFormat> responseEntityOk(@Nullable T body) {
        DefaultResponseFormat responseFormat = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                null,
                httpServletRequest.getRequestURI(),
                body
        );

        return ResponseEntity.ok(responseFormat);
    }
}
