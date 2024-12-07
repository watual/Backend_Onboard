package com.donjo.springauthcore.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ResponseUtils {

    public static ResponseEntity<MessageResponseDto> of(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
                .body(new MessageResponseDto(
                        httpStatus.value(), message
                ));
    }

    public static <T> ResponseEntity<DataResponseDto<T>> of(HttpStatus httpStatus, String message, T data) {
        return ResponseEntity.status(httpStatus)
                .body(new DataResponseDto<>(
                        httpStatus.value(), message, data
                ));
    }

    public static ResponseEntity<MessageResponseDto> success() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponseDto(
                        HttpStatus.OK.value(), "요청 성공"
                ));
    }

    public static <T> ResponseEntity<DataResponseDto<T>> success(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponseDto<>(
                        HttpStatus.OK.value(), "요청 성공", data
                ));
    }
}