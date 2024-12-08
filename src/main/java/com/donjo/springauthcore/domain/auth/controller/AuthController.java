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

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("refresh token request received");
        authService.refreshToken(request, response);
        return ResponseEntity.status(HttpStatus.OK).body("토큰이 새로 발급되었습니다.");
    }
}
