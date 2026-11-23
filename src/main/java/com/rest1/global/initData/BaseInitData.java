package com.rest1.global.initData;

import com.rest1.domain.member.service.MemberService;
import com.rest1.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    @Autowired
    @Lazy
    private BaseInitData self;
    private final PostService postService;
    private final MemberService memberService;

    @Bean
    public ApplicationRunner initData() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (postService.count() > 0) return;

        memberService.join("system", "system", "시스템");
        memberService.join("admin", "admin", "운영자");
        memberService.join("user1", "1234", "유저1");
        memberService.join("user2", "1234", "유저2");
        memberService.join("user3", "1234", "유저3");
    }

    @Transactional
    public void work2() {

        if (postService.count() > 0) return;

        postService.write("글1", "내용1");
        postService.write("글2", "내용2");
    }
}
