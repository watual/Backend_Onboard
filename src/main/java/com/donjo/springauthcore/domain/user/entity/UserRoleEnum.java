package com.donjo.springauthcore.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN")

    ;
    private final String role;
}
