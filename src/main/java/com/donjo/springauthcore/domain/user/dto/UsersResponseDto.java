package com.donjo.springauthcore.domain.user.dto;

import com.donjo.springauthcore.domain.user.entity.UserRoleEnum;
import com.donjo.springauthcore.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class UsersResponseDto {
    private String username;
    private String nickname;
    private List<AuthorityDto> authorities;

    public static UsersResponseDto from(Users users, List<UserRoleEnum> roleList) {
        return UsersResponseDto.builder()
                .username(users.getUsername())
                .nickname(users.getNickname())
                .authorities(
                        roleList.stream()
                                .map(role -> new AuthorityDto(role.name()))
                                .collect(Collectors.toList())
                )
                .build();
    }
    public static UsersResponseDto from(Users users) {
        return UsersResponseDto.builder()
                .username(users.getUsername())
                .nickname(users.getNickname())
                .authorities(
                        users.getRoleList().stream()
                                .map(role -> new AuthorityDto(role.getUserRole().name()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
