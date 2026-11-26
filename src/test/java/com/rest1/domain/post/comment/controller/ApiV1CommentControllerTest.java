package com.rest1.domain.post.comment.controller;

import com.rest1.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiV1CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("댓글 작성 성공")
    void t1() throws Exception {
        mvc.perform(post("/api/v1/posts/{postId}/comments",1)
                        .header("Authorization", "Bearer " + memberRepository.findByUsername("user1").get().getApiKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "content":"새로운 댓글"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("새로운 댓글"))
                .andExpect(jsonPath("$.authorNickname").value("유저1"));
    }
}