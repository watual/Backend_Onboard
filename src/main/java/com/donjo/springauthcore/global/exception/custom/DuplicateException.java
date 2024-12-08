package com.donjo.springauthcore.global.exception.custom;

import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import com.donjo.springauthcore.global.exception.GlobalException;

public class DuplicateException extends GlobalException {
    public DuplicateException(ExceptionCodeEnum codeEnum) {
        super(codeEnum);
    }
}
