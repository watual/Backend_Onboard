package com.donjo.springauthcore.global.auth;

import com.donjo.springauthcore.global.auth.JwtUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.security.Key;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

// TestInstane는 테스트 인스턴스의 라이프 사이클을 설정할 때 사용
// PER_METHOD: test 함수 당 인스턴스가 생성
// PER_CLASS: test 클래스 당 인스턴스가 생성
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtUtilTest {

    private static JwtUtil jwtUtil;
    private String originalKey;
    private String encodedKey;
    private Key secretKey;
    private static final String username = "testUser";

    @BeforeAll
    public void init() {
        jwtUtil = new JwtUtil();
        originalKey = "encodingTestKey1231231231231144225566788991230";
        encodedKey = jwtUtil.encodeBase64(originalKey);
        secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());
        jwtUtil.setSecretKey(encodedKey);
        jwtUtil.init();
    }

    @Test
    @DisplayName("키 인코딩 테스트")
    public void Base64_encode_test() {
        System.out.println(encodedKey);
        assertThat(originalKey, is(new String(Decoders.BASE64.decode(encodedKey))));
    }

    @Test
    @DisplayName("Access Token 생성 테스트")
    public void generate_accessToken_test() {
        String username = "access token test";

        String accessToken = jwtUtil.createAccessToken(username);

        System.out.println(accessToken);

        assertThat(accessToken, notNullValue());
    }

    @Test
    @DisplayName("Refresh Token 생성 테스트")
    public void generate_refreshToken_test() {
        String subject = "refresh token test";

        String refreshToken = jwtUtil.createRefreshToken(subject);

        System.out.println(refreshToken);

        assertThat(refreshToken, notNullValue());
    }

    @Test
    @DisplayName("유효한 토큰 검증")
    void validToken_verification_test() {
        final String accessToken = jwtUtil.createAccessToken(username).substring(7);

        // When
        boolean isValid = jwtUtil.validateToken(accessToken);

        // Then
        assertThat(isValid, is(true));

        // When & Then
        assertDoesNotThrow(() -> jwtUtil.validateToken(accessToken));
    }

    @Test
    @DisplayName("잘못된 서명을 가진 토큰 검증")
    void invalidSignatureToken_verification_test() {
        // 올바르지 않은 키로 서명된 토큰 생성
        Key invalidKey = Keys.hmacShaKeyFor("wrong-key-for-invalidSignature-test".getBytes());
        String token = io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .setIssuedAt(new Date())
                .signWith(invalidKey, SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtUtil.validateToken(token);

        assertThat(isValid, is(false));
    }

    @Test
    @DisplayName("만료된 토큰 검증")
    void expiredToken_verification_test() {
        // 만료된 토큰 생성
        String token = io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // 이미 만료된 시간 설정
                .setIssuedAt(new Date(System.currentTimeMillis() - 2000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtUtil.validateToken(token);

        assertThat(isValid, is(false));
    }

    @Test
    @DisplayName("잘못된 형식의 토큰 검증")
    void malformedToken_verification_test() {
        String token = "malformed.token";

        boolean isValid = jwtUtil.validateToken(token);

        assertThat(isValid, is(false));
    }

    @Test
    @DisplayName("토큰이 비어있는 경우 검증")
    void emptyToken_verification_test() {
        boolean isValid = jwtUtil.validateToken("");

        assertThat(isValid, is(false));
    }
}
