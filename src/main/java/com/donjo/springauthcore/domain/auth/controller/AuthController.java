package com.donjo.springauthcore.domain.auth.controller;

import com.donjo.springauthcore.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 메시지 상수화
    private static final String REFRESH_SUCCESS_MESSAGE = "토큰이 새로 발급되었습니다.";

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            log.info("refresh token request received");
            authService.refreshToken(request, response);
            return ResponseEntity.ok(REFRESH_SUCCESS_MESSAGE);
        } catch (IOException e) {
            log.error("Error refreshing token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 갱신 중 문제가 발생했습니다.");
        }
    }
}
