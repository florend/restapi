package com.florend.restapi.service;

import com.florend.restapi.model.Post;
import com.florend.restapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Post with id %d not found", id));
        }
        return post.get();
    }

    public Post addPost(Post newPost) {
        return postRepository.save(newPost);
    }

    public Post updatePost(Post newPost) {
        Optional<Post> post = postRepository.findById(newPost.getId());
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Post with id %d not found", newPost.getId()));
        }
        return postRepository.save(newPost);
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.searchPosts(keyword);
    }
}
