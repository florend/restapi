package com.florend.restapi.service;

import com.florend.restapi.model.Post;
import com.florend.restapi.payload.PostsResponse;
import com.florend.restapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


public interface PostService {
    List<Post> getAllPosts();

    List<Post> searchPosts(String keyword);

    PostsResponse getPaginated(String query, int page, int pageSize);

    Post getPostById(long id);

    Post addPost(Post newPost);

    Post updatePost(Post newPost);

    void deletePost(long id);
}
