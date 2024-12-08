package com.donjo.springauthcore.global.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final long ACCESS_TOKEN_TIME = 30 * 60 * 1000L * 60 * 60;
    public static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L;
    public static final String BEARER_PREFIX = "Bearer ";

    @Setter
    @Value("${jwt_secret_key}")
    private String secretKey;
    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    /*
    * 비밀 키 초기화
    * */
    @PostConstruct
    public void init() {
        try {
            log.info(secretKey);
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(bytes);
            log.debug("Secret key initialized success");
        } catch (IllegalArgumentException e) {
            log.error("Failed to decode Base64 secret key: {}", e.getMessage());
            throw new IllegalArgumentException("Base64 비밀 키 디코딩 실패: " + e.getMessage(), e);
        }
    }

    /*
    * Base64 인코딩
    * */
    public String encodeBase64(String decodeKey) {
        return Encoders.BASE64.encode(decodeKey.getBytes(StandardCharsets.UTF_8));
    }

    /*
    * JWT 토큰 생성
    * */
    private String createToken(String username, long expirationTime) {
        Date now = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .setIssuedAt(now)
                .signWith(key, signatureAlgorithm)
                .compact();

    }

    /*
     * 액세스 토큰 생성
     * */
    public String createAccessToken(String username) {
        return createToken(username, ACCESS_TOKEN_TIME);
    }

    /*
     * 리프레시 토큰 생성
     * */
    public String createRefreshToken(String username) {
        return createToken(username, REFRESH_TOKEN_TIME);
    }


    /*
    * 헤더에서 JWT 데이터 추출
    * */
    public String getJwtFromHeader(HttpServletRequest request, String headerName) {
        String bearerToken = request.getHeader(headerName);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    /*
     * JWT 토큰에서 사용자 정보 추출
     * */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /*
     * JWT 토큰 검증
     * */
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }
}
