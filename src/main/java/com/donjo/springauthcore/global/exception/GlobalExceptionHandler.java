package com.donjo.springauthcore.global.exception;

import com.donjo.springauthcore.global.common.response.MessageResponseDto;
import com.donjo.springauthcore.global.common.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<MessageResponseDto> handleGlobalException(GlobalException e) {
        log.error("에러: ",e);
        return ResponseUtils.of(e.getCode().getHttpStatus(), e.getMessage());
    }
}
