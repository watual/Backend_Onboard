package com.donjo.springauthcore.global.exception;

public class DuplicateException extends GlobalException {
    public DuplicateException(ExceptionCodeEnum codeEnum) {
        super(codeEnum);
    }
}
