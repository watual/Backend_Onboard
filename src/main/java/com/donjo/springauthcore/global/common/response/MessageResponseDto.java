package com.donjo.springauthcore.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponseDto {
    private Integer statusCode;
    private String message;
}
