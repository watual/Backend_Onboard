package com.donjo.springauthcore.global.exception.custom;

import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import com.donjo.springauthcore.global.exception.GlobalException;

public class UnauthorizedAccessException extends GlobalException {
    public UnauthorizedAccessException(ExceptionCodeEnum codeEnum) {
        super(codeEnum);
    }
}
