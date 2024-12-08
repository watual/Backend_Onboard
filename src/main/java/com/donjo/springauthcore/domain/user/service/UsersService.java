package com.donjo.springauthcore.domain.user.service;

import com.donjo.springauthcore.domain.user.dto.UsersResponseDto;
import com.donjo.springauthcore.domain.user.dto.UsersSignupRequestDto;
import com.donjo.springauthcore.domain.user.entity.Role;
import com.donjo.springauthcore.domain.user.entity.UserRoleEnum;
import com.donjo.springauthcore.domain.user.entity.Users;
import com.donjo.springauthcore.domain.user.repository.RoleRepository;
import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import com.donjo.springauthcore.global.exception.custom.DuplicateException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user.
     *
     * @param requestDto the signup request details
     * @return a DTO containing user details
     * @throws DuplicateException if the username or nickname already exists
     */
    @Transactional
    public UsersResponseDto signup(UsersSignupRequestDto requestDto) {
        log.info("Signup request received for username: {}", requestDto.getUsername());
        // 사용자 중복 확인
        if (usersRepository.existsByUsername(requestDto.getUsername())) {
            throw new DuplicateException(ExceptionCodeEnum.DUPLICATE_USERNAME);
        } else if (usersRepository.existsByNickname(requestDto.getNickname())) {
            throw new DuplicateException(ExceptionCodeEnum.DUPLICATE_NICKNAME);
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 저장 객체 생성
        Users users = Users.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .build();

        // DB 저장
        usersRepository.save(users);

        // 기본 Role 저장
        Role role = Role.builder()
                .users(users)
                .userRole(UserRoleEnum.ROLE_USER)
                .build();
        roleRepository.save(role);

        // Role List 조회
        List<UserRoleEnum> roleList = roleRepository.findByUsers(users).stream()
                .map(Role::getUserRole)
                .toList();


        log.info("Signup successful for username: {}", requestDto.getUsername());
        // 응답 반환
        return UsersResponseDto.from(users, roleList);
    }

    /**
     * Logs out a user by clearing their refresh token.
     *
     * @param users the user to log out
     * @throws DuplicateException if the user is already logged out
     */
    @Transactional
    public void logout(Users users) {
        log.info("Logout request received for username: {}", users.getUsername());
        if (users.getRefreshToken().isBlank()) {
            throw new DuplicateException(ExceptionCodeEnum.ALREADY_LOGGED_OUT);
        }
        users.updateRefreshToken(null);
        log.info("Logout successful for username: {}", users.getUsername());
    }
}
