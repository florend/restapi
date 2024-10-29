package com.florend.restapi.service;

import com.florend.restapi.dto.PostDto;
import com.florend.restapi.mapper.PostMapper;
import com.florend.restapi.model.Post;
import com.florend.restapi.payload.PostsResponse;
import com.florend.restapi.repository.PostRepository;
import com.florend.restapi.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void PostService_GetAllPosts_ReturnListOfPostDto() {
        List<Post> posts = mock(List.class);
        when(postRepository.findAll()).thenReturn(posts);

        List<PostDto> postDtos = postService.getAllPosts();

        Assertions.assertNotNull(postDtos);
    }

    @Test
    public void PostService_SearchPosts_ReturnListOfPostDto() {
        List<Post> posts = mock(List.class);
        when(postRepository.searchPosts("")).thenReturn(posts);

        List<PostDto> postDtos = postService.searchPosts("");

        Assertions.assertNotNull(postDtos);
    }

    @Test
    public void PostService_GetPaginated_ReturnPostsResponse() {
        Page<Post> posts = mock(Page.class);
        when(postRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(posts);

        PostsResponse postsResponse = postService.getPaginated("", 1,5);

        Assertions.assertNotNull(postsResponse);
    }

    @Test
    public void PostService_GetPostById_ReturnPostDto() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));

        PostDto savedPostDto = postService.getPostById(1L);

        Assertions.assertNotNull(savedPostDto);
    }

    @Test
    public void PostService_AddPost_ReturnPostDto() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();
        PostDto postDto = PostMapper.INSTANCE.postToPostDto(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto savedPostDto = postService.addPost(postDto);

        Assertions.assertNotNull(savedPostDto);
    }


    @Test
    public void PostService_UpdatePost_ReturnPostDto() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();
        PostDto postDto = PostMapper.INSTANCE.postToPostDto(post);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto savedPostDto = postService.updatePost(1, postDto);

        Assertions.assertNotNull(savedPostDto);
    }

    @Test
    public void PostService_DeletePost_ReturnVoid() {
        postService.deletePost(1L);

        verify(postRepository, times(1)).deleteById(1L);
    }
}
