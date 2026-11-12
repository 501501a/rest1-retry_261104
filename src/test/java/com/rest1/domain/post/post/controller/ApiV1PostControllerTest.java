package com.rest1.domain.post.post.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiV1PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("단건조회 성공")
    void t1() throws Exception {
        mvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("글1"));
    }

    @Test
    @DisplayName("단건조회 실패")
    void t2() throws Exception {
        mvc.perform(get("/api/v1/posts/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("999번 글이 존재하지 않습니다."));
    }

    @Test
    @DisplayName(("글 작성 성공"))
    void t3() throws Exception {
        mvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title":"새로운 글",
                                    "content":"새로운 내용"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("새로운 글"));
    }
}