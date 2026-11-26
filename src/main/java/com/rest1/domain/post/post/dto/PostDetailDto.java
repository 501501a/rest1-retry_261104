package com.rest1.domain.post.post.dto;

import com.rest1.domain.post.comment.dto.CommentDto;
import com.rest1.domain.post.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailDto(
        Long id,
        String title,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        List<CommentDto> comments,
        String authorNickname,
        Long authorId

) {
    public PostDetailDto(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getComments()
                        .stream()
                        .map(comment -> new CommentDto(comment))
                        .toList(),
                post.getAuthor().getNickname(),
                post.getAuthor().getId()
        );
    }
}
