package com.donjo.springauthcore.domain.auth.service;

import com.donjo.springauthcore.domain.user.entity.Users;
import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.auth.JwtUtil;
import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import com.donjo.springauthcore.global.exception.custom.NotFoundException;
import com.donjo.springauthcore.global.exception.custom.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
public class AuthService {

    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = jwtUtil.getJwtFromHeader(request, JwtUtil.REFRESH_TOKEN_HEADER);

        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedAccessException(ExceptionCodeEnum.INVALID_TOKEN);
        }
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ExceptionCodeEnum.USERNAME_NOT_FOUND));


        String newAccessToken = jwtUtil.createAccessToken(username);
        String newRefreshToken = jwtUtil.createRefreshToken(username);

        users.updateRefreshToken(newRefreshToken);
        response.setHeader("Authorization", newAccessToken);
        response.setHeader("RefreshToken", newRefreshToken);

        log.info("토큰 갱신 완료: {}", username);
    }
}
