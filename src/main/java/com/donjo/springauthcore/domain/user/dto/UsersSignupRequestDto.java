package com.donjo.springauthcore.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersSignupRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
