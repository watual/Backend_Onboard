package com.donjo.springauthcore.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersSignupRequestDto {
    /**
     * User's unique username (5-50 characters).
     * Only alphanumeric characters are allowed.
     */
    @NotBlank(message = "사용자 ID는 필수 입력 값입니다.")
//    @Size(min = 5, max = 50, message = "사용자 ID는 최소 5자 이상, 최대 50자 이하여야 합니다.")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 영문 대소문자와 숫자만 허용됩니다.")
    private String username;

    /**
     * User's password (10-50 characters).
     * Password must contain at least one letter, one digit, and one special character (!*()@#$%^&+=).
     */
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Size(min = 10, max = 50, message = "비밀번호는 최소 10자 이상, 최대 50자 이하여야 합니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!*()@#$%^&+=]).+$",
//            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 최소 1자씩 포함해야 합니다.")
    private String password;

    /**
     * User's display nickname (maximum 50 characters).
     */
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
//    @Size(max = 100, message = "닉네임은 최대 100자 이하여야 합니다.")
    private String nickname;

    /**
     * User's first name (maximum 50 characters).
     */
    @NotBlank(message = "이름은 필수 입력 값입니다.")
//    @Size(max = 100, message = "이름은 최대 100자 이하여야 합니다.")
    private String firstname;

    /**
     * User's last name (maximum 50 characters).
     */
    @NotBlank(message = "성을 입력해주세요.")
//    @Size(max = 100, message = "성은 최대 100자 이하여야 합니다.")
    private String lastname;

    /**
     * User's email address (must be a valid email format).
     */
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;
}

