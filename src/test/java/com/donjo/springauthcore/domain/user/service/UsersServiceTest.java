package com.donjo.springauthcore.domain.user.service;

import com.donjo.springauthcore.domain.user.dto.UsersResponseDto;
import com.donjo.springauthcore.domain.user.dto.UsersSignupRequestDto;
import com.donjo.springauthcore.domain.user.entity.Users;
import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.exception.DuplicateException;
import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void testSignupSuccess() {
        // Given
        String user = "testUser";
        String password = "password123";
        String encodedPassword = "encodedPassword";
        UsersSignupRequestDto requestDto = UsersSignupRequestDto.builder()
                .username(user)
                .password(password)
                .build();

        // userRepository.existsByUsername() 호출 시 false를 반환하도록 설정
        when(usersRepository.existsByUsername(user)).thenReturn(false);

        // Stub: passwordEncoder.encode는 "encodedPassword" 반환
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // When
        UsersResponseDto responseDto = usersService.signup(requestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals(user, responseDto.getUsername());

        // Verify: 메서드 호출 여부 검증
        verify(usersRepository).existsByUsername(user);
        verify(passwordEncoder).encode(password);
        verify(usersRepository).save(any(Users.class));
    }

    @Test
    @DisplayName("회원 아이디 중복 테스트")
    void testSignupDuplicateUser() {
        // Given
        String username = "testuser";
        String password = "password123";
        UsersSignupRequestDto requestDto = UsersSignupRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        // Stub: existsByUsername() 호출 시 true 반환 (중복 사용자 시나리오)
        when(usersRepository.existsByUsername(username)).thenReturn(true);

        // When & Then: DuplicateException 발생 검증
        DuplicateException exception = assertThrows(
                DuplicateException.class,
                () -> usersService.signup(requestDto)
        );

        // 검증: 예외 메시지나 코드 확인 (선택)
        assertEquals(ExceptionCodeEnum.DUPLICATE_USERNAME, exception.getCode());

        // Verify: 호출 여부 검증
        verify(usersRepository).existsByUsername(username); // existsByUsername() 호출 검증
        verify(usersRepository, never()).save(any(Users.class)); // save()는 호출되지 않아야 함
        verifyNoInteractions(passwordEncoder); // passwordEncoder는 호출되지 않아야 함
    }
}