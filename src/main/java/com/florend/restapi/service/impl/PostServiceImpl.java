package com.florend.restapi.service.impl;

import com.florend.restapi.dto.PostDto;
import com.florend.restapi.mapper.PostMapper;
import com.florend.restapi.model.Post;
import com.florend.restapi.payload.PostsResponse;
import com.florend.restapi.repository.PostRepository;
import com.florend.restapi.repository.specification.PostSpecification;
import com.florend.restapi.service.PostService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.florend.restapi.config.CacheConstants.*;

@Service
public class PostServiceImpl  implements PostService {
    final PostRepository postRepository;

    PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Cacheable(value = POSTS_CACHE)
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostMapper.INSTANCE::postToPostDto).collect(Collectors.toList());
    }

    @Cacheable(value = POSTS_CACHE)
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchPosts(keyword);
        return posts.stream().map(PostMapper.INSTANCE::postToPostDto).collect(Collectors.toList());
    }

    @Cacheable(value = POSTS_RESPONSE_CACHE)
    @Override
    public PostsResponse getPaginated(String query, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Specification<Post> hasTitleLike = PostSpecification.hasTitleLike(query);
        Page<Post> posts = postRepository.findAll(hasTitleLike, pageable);
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setItems(posts.stream().map(PostMapper.INSTANCE::postToPostDto).collect(Collectors.toList()));
        postsResponse.setTotalCount(posts.getTotalElements());
        postsResponse.setTotalPages(posts.getTotalPages());
        return postsResponse;
    }

    @Cacheable(value = POST_CACHE, key = "#id")
    @Override
    public PostDto getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Post with id %d not found", id));
        }
        return PostMapper.INSTANCE.postToPostDto(post.get());
    }

    @Caching(evict = {
            @CacheEvict(value = POSTS_CACHE, allEntries = true),
            @CacheEvict(value = POSTS_RESPONSE_CACHE, allEntries = true)
    })
    @Override
    public PostDto addPost(PostDto newPost) {
        Post newEntity = PostMapper.INSTANCE.postDtoToPost(newPost);
        Post savedPost = postRepository.save(newEntity);
        return PostMapper.INSTANCE.postToPostDto(savedPost);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = POSTS_CACHE, allEntries = true),
                    @CacheEvict(value = POSTS_RESPONSE_CACHE, allEntries = true)
            },
            put = {@CachePut(value = POST_CACHE, key = "#id")}
    )
    @Override
    public PostDto updatePost(long id, PostDto newPost) {
        newPost.setId(id);
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Post with id %d not found", newPost.getId()));
        }
        Post updatedPost = postRepository.save(PostMapper.INSTANCE.postDtoToPost(newPost));
        return PostMapper.INSTANCE.postToPostDto(updatedPost);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = POSTS_CACHE, allEntries = true),
                    @CacheEvict(value = POSTS_RESPONSE_CACHE, allEntries = true),
                    @CacheEvict(value = POST_CACHE, key = "#id")
            }
    )
    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
