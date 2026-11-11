package com.rest1.domain.post.post.dto;

import com.rest1.domain.post.post.entity.Post;

import java.time.LocalDateTime;

public record PostListItemDto(
        Long id,
        String title,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate
) {
    public PostListItemDto(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreateDate(),
                post.getModifyDate()
        );
    }
}
