package com.rest1.domain.post.post.service;

import com.rest1.domain.post.post.entity.Post;
import com.rest1.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        Post post = findPost(id);
        return post;
    }

    public Post write(String title, String content) {
        Post post = new Post(title, content);
        return postRepository.save(post);
    }

    public long count() {
        return postRepository.count();
    }

    @Transactional
    public Post modify(Long id, String title, String content) {
        Post post = findPost(id);
        post.modify(title, content);
        return post;
    }

    @Transactional
    public void delete(Long id) {
        Post post = findPost(id);
        postRepository.delete(post);
    }

    private Post findPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("%d번 글이 존재하지 않습니다.".formatted(id)));
        return post;
    }
}
