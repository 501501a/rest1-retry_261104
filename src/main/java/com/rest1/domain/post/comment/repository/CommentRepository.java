package com.rest1.domain.post.comment.repository;

import com.rest1.domain.post.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
