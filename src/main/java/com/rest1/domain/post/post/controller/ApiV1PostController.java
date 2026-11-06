package com.rest1.domain.post.post.controller;

import com.rest1.domain.post.post.entity.Post;
import com.rest1.domain.post.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiV1PostController {
    private final PostService postService;

    public ApiV1PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/v1/posts")
    public List<Post> getItems() {
        return postService.findAll();
    }


    @GetMapping("/api/v1/posts/{id}")
    public Post getItem(
            @PathVariable Long id
    ) {
        return postService.findById(id);
    }

    record PostWriteReqBody(
            String title,
            String content
    ) {
    }

    @PostMapping("/api/v1/posts")
    public Post write(
            @RequestBody PostWriteReqBody reqBody
    ) {
       return postService.write(reqBody.title(), reqBody.content());

    }

    record PostModifyReqBody (
            String title,
            String content
    ){ }

    @PutMapping("/api/v1/posts/{id}")
    public Post modity (
            @PathVariable Long id,
            @RequestBody PostModifyReqBody reqBody
    ){
       return postService.modify(id, reqBody.title(), reqBody.content());
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public String delete(
            @PathVariable Long id
    ){
        postService.delete(id);
        return "삭제완료";
    }
}
