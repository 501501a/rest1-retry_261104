package com.rest1.domain.post.post.controller;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.service.MemberService;
import com.rest1.domain.post.post.dto.PostDetailDto;
import com.rest1.domain.post.post.dto.PostListItemDto;
import com.rest1.domain.post.post.entity.Post;
import com.rest1.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiV1PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/api/v1/posts")
    public List<PostListItemDto> getItems() {
        List<Post> posts = postService.findAll();
        return posts.stream()
                .map(post -> new PostListItemDto(post))
                .toList();
    }


    @GetMapping("/api/v1/posts/{id}")
    public PostDetailDto getItem(
            @PathVariable Long id
    ) {
        Post post = postService.findById(id);
        PostDetailDto postDto = new PostDetailDto(post);
        return postDto;
    }

    record PostWriteReqBody(
            String title,
            String content
    ) {
    }

    @PostMapping("/api/v1/posts")
    public PostDetailDto write(
            @RequestBody PostWriteReqBody reqBody
    ) {
        String findName = SecurityContextHolder.getContext().getAuthentication().getName();
        Member actor = memberService.findByUsername(findName).get();

        Post post = postService.write(actor, reqBody.title(), reqBody.content());
        return new PostDetailDto(post);
    }

    record PostModifyReqBody(
            String title,
            String content
    ) {
    }

    @PutMapping("/api/v1/posts/{id}")
    public PostDetailDto modify(
            @PathVariable Long id,
            @RequestBody PostModifyReqBody reqBody
    ) {
        Post post = postService.modify(id, reqBody.title(), reqBody.content());
        return new PostDetailDto(post);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public String delete(
            @PathVariable Long id
    ) {
        postService.delete(id);
        return "삭제완료";
    }
}
