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

    /**
     * Create a UsersResponseDto from a Users entity and a list of roles.
     *
     * @param users    Users entity
     * @param roleList List of user roles
     * @return UsersResponseDto
     */
    public static UsersResponseDto from(Users users, List<UserRoleEnum> roleList) {
        return UsersResponseDto.builder()
                .username(users.getUsername())
                .nickname(users.getNickname())
                .authorities(mapToAuthorityDto(roleList))
                .build();
    }

    /**
     * Create a UsersResponseDto from a Users entity.
     *
     * @param users Users entity
     * @return UsersResponseDto
     */
    public static UsersResponseDto from(Users users) {
        return UsersResponseDto.builder()
                .username(users.getUsername())
                .nickname(users.getNickname())
                .authorities(mapToAuthorityDto(users.getRoleList()))
                .build();
    }

    private static List<AuthorityDto> mapToAuthorityDto(List<?> roles) {
        return roles.stream()
                .map(role -> new AuthorityDto(role.toString()))
                .collect(Collectors.toList());
    }
}
