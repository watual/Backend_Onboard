package com.donjo.springauthcore.domain.auth.controller;

import com.donjo.springauthcore.domain.auth.service.AuthService;
import com.donjo.springauthcore.global.auth.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 메시지 상수화
    private static final String REFRESH_SUCCESS_MESSAGE = "토큰이 새로 발급되었습니다.";

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("refresh token request received");
        authService.refreshToken(request, response);
        return ResponseEntity.ok(REFRESH_SUCCESS_MESSAGE);
    }

    @GetMapping("/gettest")
    public void getTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("get test request received");
    }


    @PostMapping("/posttest")
    public void postTest(@RequestBody LoginRequestDto requestDto) throws IOException {
        log.info("post test request received");
        log.info("requestDto: {}, {}", requestDto.getUsername(), requestDto.getPassword());
    }
}
