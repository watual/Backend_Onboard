package com.donjo.springauthcore.domain.user.service;

import com.donjo.springauthcore.domain.user.dto.UsersResponseDto;
import com.donjo.springauthcore.domain.user.dto.UsersSignupRequestDto;
import com.donjo.springauthcore.domain.user.entity.Users;
import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.exception.DuplicateException;
import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersResponseDto signup(UsersSignupRequestDto requestDto) {
        // 사용자 중복 확인
        if(usersRepository.existsByUsername(requestDto.getUsername())) {
            throw new DuplicateException(ExceptionCodeEnum.DUPLICATE_USERNAME);
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 저장 객체 생성
        Users users = Users.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword)
                .build();

        // DB 저장
        usersRepository.save(users);

        // 응답 반환
        return UsersResponseDto.from(users);
    }
}
