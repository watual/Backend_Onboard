package com.donjo.springauthcore.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersSignupRequestDto {
    /**
     * User's unique username (4-20 characters).
     */
    @NotBlank
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    /**
     * User's password (minimum 8 characters).
     * This field should be encrypted before saving to the database.
     */
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    /**
     * User's display nickname (maximum 50 characters).
     */
    @NotBlank
    @Size(max = 50, message = "Nickname must be at most 50 characters")
    private String nickname;
}
