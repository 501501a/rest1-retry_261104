package com.rest1.domain.post.comment.service;

import com.rest1.domain.post.comment.dto.CommentDto;
import com.rest1.domain.post.comment.entity.Comment;
import com.rest1.domain.post.comment.repository.CommentRepository;
import com.rest1.domain.post.post.entity.Post;
import com.rest1.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDto write(Long id, String content) {
        Post targetPost = postService.findById(id);
        Comment comment = new Comment(targetPost, content);
        Comment savedComment = commentRepository.save(comment);
        CommentDto commentDto = new CommentDto(savedComment);
        return commentDto;
    }

    @Transactional
    public CommentDto modify(Long postId, Long commentId, String content) {
        Comment comment = findCommentOfPost(postId, commentId);

        comment.modify(content);
        CommentDto commentDto = new CommentDto(comment);
        return commentDto;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getComments(Long postId) {
        Post post = postService.findById(postId);
        return post.getComments()
                .stream()
                .map(comment -> new CommentDto(comment))
                .toList();
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(Long postId, Long commentId) {
        Comment comment = findCommentOfPost(postId, commentId);

        CommentDto commentDto = new CommentDto(comment);
        return commentDto;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = findCommentOfPost(postId, commentId);

        commentRepository.delete(comment);
    }

    private Comment findCommentOfPost(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("%d번 댓글이 존재하지 않습니다.".formatted(commentId)));
        Long commentPostId = comment.getPost().getId();

        if (!postId.equals(commentPostId)) {
            throw new IllegalArgumentException("해당 글의 댓글이 아닙니다.");
        }
        return comment;
    }
}
