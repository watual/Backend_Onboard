package com.donjo.springauthcore.global.auth;

import com.donjo.springauthcore.domain.user.entity.Users;
import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.exception.ExceptionCodeEnum;
import com.donjo.springauthcore.global.exception.custom.NotFoundException;
import com.donjo.springauthcore.global.exception.custom.UnauthorizedAccessException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "인가 필터")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthorizationFilter");

        String requestUrl = request.getRequestURL().toString();
        if (    requestUrl.contains("/auth") ||
                requestUrl.contains("/login") ||
                requestUrl.contains("/h2-console") ||
                requestUrl.contains("/swagger") ||
                requestUrl.contains("/v3/api-docs")
        ) {
            log.info("특정 경로 요청, 필터를 건너뜁니다: {}", requestUrl);
            filterChain.doFilter(request, response); // 필터 체인을 계속 진행
            return;
        }
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String token = jwtUtil.getJwtFromHeader(request, JwtUtil.AUTHORIZATION_HEADER);
        log.info("주소: " + requestUrl);
        log.info("Authorization token: {}", request.getHeader("Authorization"));
        log.info("token : " + token);

        if (StringUtils.hasText(token)) {
            log.info("토큰 검수 시행");
            if (!jwtUtil.validateToken(token)) {
                log.error("Token error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(token);

            // 로그아웃 된 유저 걸러내기, 로그아웃 하면 accesstoken 살아있어도 인가 막아버림
            String username = jwtUtil.getUserInfoFromToken(token).getSubject();
            try {
                Users users = usersRepository.findByUsername(username).orElseThrow(
                        () -> new NotFoundException(ExceptionCodeEnum.USERNAME_NOT_FOUND)
                );
                if (users.getRefreshToken() == null) {
                    throw new UnauthorizedAccessException(ExceptionCodeEnum.LOGGED_OUT);
                }
                setAuthentication(info.getSubject());   // permit
            } catch (Exception e) {
                log.error(e.getMessage());
                response.setContentType("application/json; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
                response.getWriter().flush();
                return;
            }
        }
        log.info("AuthorizationFilter");
        filterChain.doFilter(request, response);

    }

    // authenticate
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // create authentication
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
