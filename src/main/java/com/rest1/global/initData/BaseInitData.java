package com.rest1.global.initData;

import com.rest1.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;

    @Bean
    public ApplicationRunner initData() {
        return args -> {
            if (postService.count() > 0) return;
            postService.write("글1", "내용1");
            postService.write("글2", "내용2");
        };
    }
}
