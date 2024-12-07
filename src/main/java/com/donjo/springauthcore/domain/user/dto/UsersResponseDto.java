package com.donjo.springauthcore.domain.user.dto;

import com.donjo.springauthcore.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UsersResponseDto {
    private String username;

    public static UsersResponseDto from(Users users) {
        return UsersResponseDto.builder()
                .username(users.getUsername())
                .build();
    }
}
