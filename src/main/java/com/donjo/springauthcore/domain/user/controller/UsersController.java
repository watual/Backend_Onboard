package com.donjo.springauthcore.domain.user.controller;

import com.donjo.springauthcore.domain.user.dto.UsersSignupRequestDto;
import com.donjo.springauthcore.domain.user.dto.UsersResponseDto;
import com.donjo.springauthcore.domain.user.service.UsersService;
import com.donjo.springauthcore.global.auth.UserDetailsImpl;
import com.donjo.springauthcore.global.common.response.DataResponseDto;
import com.donjo.springauthcore.global.common.response.MessageResponseDto;
import com.donjo.springauthcore.global.common.response.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    /*
     * 회원가입
     * */
    @PostMapping("/signup")
    public ResponseEntity<DataResponseDto<UsersResponseDto>> signup(@Valid @RequestBody UsersSignupRequestDto requestDto) {
        UsersResponseDto userResponseDto = usersService.signup(requestDto);
        return ResponseUtils.success(userResponseDto);
    }

    /*
     * 사용자(본인) 정보 조회
     * */
    @GetMapping("/myProfile")
    public ResponseEntity<DataResponseDto<UsersResponseDto>> myProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.success(UsersResponseDto.from(userDetails.getUser()));
    }

    /*
     * 로그아웃
     * */
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        usersService.logout(userDetails.getUser());
        return ResponseUtils.success();
    }
}
