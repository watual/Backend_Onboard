package com.donjo.springauthcore.domain.user.controller;

import com.donjo.springauthcore.domain.user.dto.UsersResponseDto;
import com.donjo.springauthcore.domain.user.dto.UsersSignupRequestDto;
import com.donjo.springauthcore.domain.user.service.UsersService;
import com.donjo.springauthcore.global.common.response.DataResponseDto;
import com.donjo.springauthcore.global.common.response.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public <T> ResponseEntity<DataResponseDto<UsersResponseDto>> signup(@Valid @RequestBody UsersSignupRequestDto requestDto) {
        UsersResponseDto userResponseDto = usersService.signup(requestDto);
        return ResponseUtils.success(userResponseDto);
    }
}
