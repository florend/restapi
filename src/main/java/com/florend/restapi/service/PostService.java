package com.florend.restapi.service;

import com.florend.restapi.dto.PostDto;
import com.florend.restapi.payload.PostsResponse;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    List<PostDto> searchPosts(String keyword);

    PostsResponse getPaginated(String query, int page, int pageSize);

    PostDto getPostById(long id);

    PostDto addPost(PostDto newPost);

    PostDto updatePost(PostDto newPost);

    void deletePost(long id);
}
