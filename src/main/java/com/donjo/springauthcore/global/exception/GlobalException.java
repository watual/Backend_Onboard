package com.donjo.springauthcore.global.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final ExceptionCodeEnum code;

    public GlobalException(ExceptionCodeEnum code) {
        super(code.getMessage());
        this.code = code;
    }
}
