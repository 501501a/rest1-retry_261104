package com.rest1.domain.post.post.controller;

import com.rest1.domain.post.post.dto.PostDetailDto;
import com.rest1.domain.post.post.dto.PostListItemDto;
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
       Post post = postService.write(reqBody.title(), reqBody.content());
       return new PostDetailDto(post);
    }

    record PostModifyReqBody (
            String title,
            String content
    ){ }

    @PutMapping("/api/v1/posts/{id}")
    public PostDetailDto modify (
            @PathVariable Long id,
            @RequestBody PostModifyReqBody reqBody
    ){
       Post post = postService.modify(id, reqBody.title(), reqBody.content());
       return  new PostDetailDto(post);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public String delete(
            @PathVariable Long id
    ){
        postService.delete(id);
        return "삭제완료";
    }
}
