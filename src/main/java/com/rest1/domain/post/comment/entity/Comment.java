package com.rest1.domain.post.comment.entity;

import com.rest1.domain.post.post.entity.Post;
import com.rest1.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {
    private String content;

    @ManyToOne
    private Post post;

    public Comment(Post post, String content){
        this.post = post;
        this.content = content;
    }

    public void modify(String content){
        this.content = content;
    }
}
