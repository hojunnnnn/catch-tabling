package com.catchtabling.common.exception.handler;

import com.catchtabling.common.dto.DefaultResponseFormat;
import com.catchtabling.common.exception.customex.AlreadyReservedException;
import com.catchtabling.common.exception.customex.BadRequestException;
import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.NotFoundException;
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
        DefaultResponseFormat errorResponse = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(ErrorCode.INVALID_REQUEST)
        );

        return ResponseEntity.status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultResponseFormat> handle(IllegalArgumentException e) {
        logWarn(e);
        DefaultResponseFormat errorResponse = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(ErrorCode.INVALID_REQUEST)
        );

        return ResponseEntity.status(httpStatus)
                .body(errorResponse);
    }

    // todo 같은 에러에 대해 다른 errorCode 응답이 필요한데..
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<DefaultResponseFormat> handle(IllegalStateException e) {
        logWarn(e);
        DefaultResponseFormat errorResponse = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(ErrorCode.INVALID_REQUEST)
        );

        return ResponseEntity.status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultResponseFormat> handle(BadRequestException e) {
        logWarn(e);
        DefaultResponseFormat errorResponse = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(e.getErrorCode())
        );

        return ResponseEntity.status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(AlreadyReservedException.class)
    public ResponseEntity<DefaultResponseFormat> handle(AlreadyReservedException e) {
        logWarn(e);
        DefaultResponseFormat errorResponse = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(e.getErrorCode())
        );

        return ResponseEntity.status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultResponseFormat> handle(NotFoundException e) {
        logWarn(e);
        DefaultResponseFormat errorResponse = new DefaultResponseFormat(
                LocalDateTime.now().toString(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                request.getRequestURI(),
                ErrorResponse.from(e.getErrorCode())
        );

        return ResponseEntity.status(httpStatus)
                .body(errorResponse);
    }

    private void logWarn(Exception e) {
        log.warn(LOG_FORMAT_WARN,
                request.getMethod(), request.getRequestURI(), e.getMessage(), e);
    }
}
