package com.florend.restapi.controller;

import com.florend.restapi.model.Post;
import com.florend.restapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> addPost(@RequestBody Post post) {
        try {
            return new ResponseEntity<>(postService.addPost(post), HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<String> updatePost(@PathVariable int id, @RequestBody Post post) {
        post.setId(id);
        Post newPost = postService.updatePost(post);

        if (newPost != null)
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
         else
           return new ResponseEntity<>("Failed to update post", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            postService.deletePost(id);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete post, not found", HttpStatus.NOT_FOUND);
        }
    }
}
