package com.donjo.springauthcore.global.auth;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {
    String username() default "testUser";
    String password() default "testPassword";
    String nickname() default "testNickname";
    String[] roles() default {"ROLE_USER"};
}
