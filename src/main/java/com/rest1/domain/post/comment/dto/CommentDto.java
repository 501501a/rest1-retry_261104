package com.rest1.domain.post.comment.dto;

import com.rest1.domain.post.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        Long postId
) {
   public CommentDto(Comment comment){
        this(
                comment.getId(),
                comment.getContent(),
                comment.getCreateDate(),
                comment.getModifyDate(),
                comment.getPost().getId()
        );
    }
}
