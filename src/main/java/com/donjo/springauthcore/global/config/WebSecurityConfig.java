package com.donjo.springauthcore.global.config;

import com.donjo.springauthcore.domain.user.repository.UsersRepository;
import com.donjo.springauthcore.global.auth.JwtAuthenticationFilter;
import com.donjo.springauthcore.global.auth.JwtAuthorizationFilter;
import com.donjo.springauthcore.global.auth.JwtUtil;
import com.donjo.springauthcore.global.auth.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService, usersRepository);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, usersRepository, objectMapper);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // csrf 설정
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // CORS 설정 추가
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 기본 설정인 Session 사용 안하고 JWT 사용하기 위한 설정
        httpSecurity.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 허용 주소: 관리[auth], 회원가입, 로그인, 콘솔, 스웨거, 에러, 프론트 페이지
        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/signup").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/error").permitAll()  // 서버 단에서 에러시 에러창 띄워주는 url
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
        );

        // 헤더 설정: FrameOptions 비활성화
        httpSecurity.headers((headers) ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
        );

        // 필터관리
        httpSecurity.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080",
                "http://localhost:63342",
                "https://localhost:8080",
                "https://localhost:63342",
                "https://www.filmfly.life",
                "https://api.filmfly.life"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
