package com.catchtabling.common.exception.handler;

import com.catchtabling.common.dto.DefaultResponseFormat;
import com.catchtabling.common.exception.customex.*;
import com.catchtabling.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExHandler {

    private static final String LOG_FORMAT_INFO = "\n[🟢INFO] - ({} {}) ({})";
    private static final String LOG_FORMAT_WARN = "\n[🟠WARN] - ({} {}) ({})";
    private static final String LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {}) ({})";

    private final HttpServletRequest request;
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultResponseFormat> handle(ConstraintViolationException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponse(ErrorCode.INVALID_REQUEST));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultResponseFormat> handle(IllegalArgumentException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponse(ErrorCode.INVALID_REQUEST));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<DefaultResponseFormat> handle(IllegalStateException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponse(ErrorCode.INVALID_REQUEST));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultResponseFormat> handle(BadRequestException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(AlreadyReservedException.class)
    public ResponseEntity<DefaultResponseFormat> handle(AlreadyReservedException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultResponseFormat> handle(NotFoundException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<DefaultResponseFormat> handle(ValidException e) {
        logWarn(e);
        return ResponseEntity.status(httpStatus)
                .body(getErrorResponseWithMessage(e.getErrorCode(), e.getMessage()));
    }

    private void logWarn(Exception e) {
        log.warn(LOG_FORMAT_WARN,
                request.getMethod(), request.getRequestURI(), e.getMessage(), e);
    }

    private DefaultResponseFormat getErrorResponse(ErrorCode errorCode) {
        return new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(errorCode)
        );
    }
    private DefaultResponseFormat getErrorResponseWithMessage(ErrorCode errorCode, String message) {
        return new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(errorCode, message)
        );
    }
}
