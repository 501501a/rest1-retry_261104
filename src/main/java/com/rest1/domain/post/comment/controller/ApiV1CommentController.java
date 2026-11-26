package com.rest1.domain.post.comment.controller;

import com.rest1.domain.member.entity.Member;
import com.rest1.domain.member.service.MemberService;
import com.rest1.domain.post.comment.dto.CommentDto;
import com.rest1.domain.post.comment.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiV1CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    record PostCommentsReqBody(
            @NotBlank String content
    ) {
    }

    @PostMapping("/api/v1/posts/{postId}/comments")
    public CommentDto write(
            @PathVariable Long postId,
            @Valid @RequestBody PostCommentsReqBody reqBody
    ) {
        String findName = SecurityContextHolder.getContext().getAuthentication().getName();
        Member actor = memberService.findByUsername(findName).get();

        CommentDto comment = commentService.write(actor, postId, reqBody.content());
        return comment;
    }

    record PostCommentModifyReqBody(
            @NotBlank String content
    ) {
    }

    @PutMapping("/api/v1/posts/{postId}/comments/{id}")
    public CommentDto modify(
            @PathVariable Long postId,
            @PathVariable Long id,
            @Valid @RequestBody PostCommentModifyReqBody reqBody
    ) {
        CommentDto commentModify = commentService.modify(postId, id, reqBody.content());
        return commentModify;
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    public List<CommentDto> list(
            @PathVariable Long postId
    ) {
        return commentService.getComments(postId);
    }

    @GetMapping("/api/v1/posts/{postId}/comments/{id}")
    public CommentDto detail(
            @PathVariable Long postId,
            @PathVariable Long id
    ) {
        return commentService.getComment(postId, id);
    }

    @DeleteMapping("/api/v1/posts/{postId}/comments/{id}")
    public String delete(
            @PathVariable Long postId,
            @PathVariable Long id
    ) {
        commentService.deleteComment(postId, id);
        return "삭제 완료";
    }
}
