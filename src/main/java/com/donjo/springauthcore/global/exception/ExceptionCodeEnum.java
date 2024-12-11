package com.donjo.springauthcore.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCodeEnum {
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 이름입니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
    LOGGED_OUT(HttpStatus.BAD_REQUEST, "로그아웃된 사용자입니다."),
    ALREADY_LOGGED_OUT(HttpStatus.BAD_REQUEST, "이미 로그아웃된 사용자입니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 Email 입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
