package com.donjo.springauthcore.global.exception.custom;

import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import com.donjo.springauthcore.global.exception.GlobalException;

public class NotFoundException extends GlobalException {
    public NotFoundException(ExceptionCodeEnum codeEnum) {
        super(codeEnum);
    }
}