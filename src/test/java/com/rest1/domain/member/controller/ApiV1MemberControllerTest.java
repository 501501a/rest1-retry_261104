package com.rest1.domain.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
}


