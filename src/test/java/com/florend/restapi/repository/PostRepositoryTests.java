package com.florend.restapi.repository;

import com.florend.restapi.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void PostRepository_AddPost_ReturnSavedPost() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        Assertions.assertNotNull(savedPost);
        Assertions.assertTrue(savedPost.getId() > 0);
    }

    @Test
    public void PostRepository_FindAll_ReturnPosts() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();
        Post post2 = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();
        postRepository.save(post);
        postRepository.save(post2);

        List<Post> posts = postRepository.findAll();

        Assertions.assertNotNull(posts);
        Assertions.assertEquals(27, posts.size());
    }

    @Test
    public void PostRepository_FindById_ReturnPost() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        Post foundPost = postRepository.findById(savedPost.getId()).get();

        Assertions.assertNotNull(foundPost);
    }

    @Test
    public void PostRepository_UpdatePost_ReturnPost() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        Post foundPost = postRepository.findById(savedPost.getId()).get();

        String image = "https://placecats.com/millie/300/200";
        String title = "New title";
        String body = "New body";
        foundPost.setImage(image);
        foundPost.setTitle(title);
        foundPost.setBody(body);
        Post updatedPost = postRepository.save(foundPost);

        Assertions.assertEquals(image, updatedPost.getImage());
        Assertions.assertEquals(title, updatedPost.getTitle());
        Assertions.assertEquals(body, updatedPost.getBody());
    }

    @Test
    public void PostRepository_DeletePost_ReturnPostIsEmpty() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        postRepository.deleteById(savedPost.getId());
        Optional<Post> foundPost = postRepository.findById(savedPost.getId());

        Assertions.assertTrue(foundPost.isEmpty());
    }

    @Test
    public void PostRepository_SearchPosts_ReturnPosts() {
        Post post = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title")
                .body("My body")
                .date(LocalDateTime.now())
                .build();
        Post post2 = Post.builder()
                .image("https://placecats.com/neo/300/200")
                .title("My title2")
                .body("My body")
                .date(LocalDateTime.now())
                .build();
        postRepository.save(post);
        postRepository.save(post2);

        List<Post> posts = postRepository.searchPosts("title");

        Assertions.assertNotNull(posts);
        Assertions.assertEquals(2, posts.size());
    }
}
