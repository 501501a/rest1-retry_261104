package com.rest1.domain.post.post.service;

import com.rest1.domain.post.post.entity.Post;
import com.rest1.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
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
        Post post = postRepository.findById(id).get();
        post.modify(title, content);
        return post;
    }

    @Transactional
    public void delete(Long id){
       Post post = postRepository.findById(id).get();
       postRepository.delete(post);
    }
}
