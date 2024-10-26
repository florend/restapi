package com.florend.restapi.service.impl;

import com.florend.restapi.model.Post;
import com.florend.restapi.payload.PostsResponse;
import com.florend.restapi.repository.PostRepository;
import com.florend.restapi.repository.specification.PostSpecification;
import com.florend.restapi.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl  implements PostService {
    final PostRepository postRepository;

    PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return postRepository.searchPosts(keyword);
    }

    @Override
    public PostsResponse getPaginated(String query, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Specification<Post> hasTitleLike = PostSpecification.hasTitleLike(query);
        Page<Post> posts = postRepository.findAll(hasTitleLike, pageable);
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setItems(posts.getContent());
        postsResponse.setTotalCount(posts.getTotalElements());
        postsResponse.setTotalPages(posts.getTotalPages());
        return postsResponse;
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Post with id %d not found", id));
        }
        return post.get();
    }

    @Override
    public Post addPost(Post newPost) {
        return postRepository.save(newPost);
    }

    @Override
    public Post updatePost(Post newPost) {
        Optional<Post> post = postRepository.findById(newPost.getId());
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Post with id %d not found", newPost.getId()));
        }
        return postRepository.save(newPost);
    }
    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
