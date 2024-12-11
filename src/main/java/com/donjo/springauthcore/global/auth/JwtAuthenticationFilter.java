package com.donjo.springauthcore.global.auth;

import com.donjo.springauthcore.domain.user.entity.Users;
import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.auth.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "인증 필터")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UsersRepository usersRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{

            log.info("들어는 왔다");

            int contentLength = request.getContentLength();

            log.info("길이: "+contentLength);


            LoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            log.info(requestDto.getUsername() + " : " + requestDto.getPassword());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch(IOException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        String username = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
        Users users = usersRepository.findByUsername(username).orElseThrow(NullPointerException::new);

        String accessToken = jwtUtil.createAccessToken(username);
        String refreshToken = jwtUtil.createRefreshToken(username);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
        users.updateRefreshToken(refreshToken);
        usersRepository.save(users);

        // 로그인 성공 메세지 반환
        log.info("로그인 성공");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString("로그인 성공"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString("로그인 실패"));
        response.setStatus(401);
    }
}
