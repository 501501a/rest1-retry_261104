package com.rest1.domain.member.controller;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.repository.MemberRepository;
import com.rest1.domain.member.service.AuthTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiV1MemberControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthTokenService authTokenService;

    @Test
    @DisplayName("회원가입")
    void t1() throws Exception {
        mvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username":"newUser",
                                    "password":"1234",
                                    "nickname":"새유저"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newUser"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("아이디 중복 가입")
    void t2() throws Exception {
        mvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username":"user1",
                                    "password":"1234",
                                    "nickname":"유저1"
                                }
                                """)
                )
                .andExpect(status().isConflict());
    }


    @Test
    @DisplayName("로그인 성공")
    void t3() throws Exception {
        mvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username":"user1",
                                    "password":"1234"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiKey").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }


    @Test
    @DisplayName("잘못된 비밀번호 요청")
    void t4() throws Exception {
        mvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username":"user1",
                                    "password":"1111"
                                }
                                """)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("존재 하지 않은 아이디 로그인")
    void t5() throws Exception {
        mvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username":"nobody",
                                    "password":"1111"
                                }
                                """)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("내 정보")
    void t6() throws Exception {
        mvc.perform(get("/api/v1/members/me")
                        .header("Authorization", "Bearer " + memberRepository.findByUsername("user1").get().getApiKey())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    @DisplayName("엉터리 apiKey + 유효한 accessToken → 내 정보 조회 성공")
    void t7() throws Exception {

        Member member = memberRepository.findByUsername("user1").get();

        mvc.perform(get("/api/v1/members/me")
                        .header("Authorization", "Bearer " + "apiKey-123"+ " " + authTokenService.genAccessToken(member))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }
}


