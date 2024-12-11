package com.donjo.springauthcore.domain.user.controller;

import com.donjo.springauthcore.global.auth.WithCustomMockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("myProfile 테스트")
    @DirtiesContext
    @WithCustomMockUser
    public void api_myProfile_test() throws Exception {
        //조회 테스트
        mockMvc.perform(get("/api/myProfile"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 상태 코드 200 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("testUser")) // JSON 응답에서 username 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("testNickname")); // JSON 응답에서 nickname 확인
    }
}