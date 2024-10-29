package com.florend.restapi.controller;

import com.florend.restapi.dto.PostDto;
import com.florend.restapi.payload.PostsResponse;
import com.florend.restapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post Controller", description = "All Post related APIs")
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Retrieve all posts",
            description = "Get all posts in the database",
            tags = { "posts", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class)), mediaType = "application/json")})
    })
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(
            summary = "Retrieve posts with pagination",
            description = "Get a PostsResponse object by specifying a search query, a page index and a page size.",
            tags = { "posts", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PostsResponse.class), mediaType = "application/json")})
    })
    @GetMapping("/paginated")
    public PostsResponse getPaginatedPosts(
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "5") int pageSize
    ) {
        return postService.getPaginated(query, page, pageSize);
    }

    @Operation(
            summary = "Retrieve one post by id",
            description = "Get one post by providing its id in the url",
            tags = { "post", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PostDto.class), mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Create a post",
            description = "Create a post by sending a post object through the request body",
            tags = { "post" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = PostDto.class), mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto post) {
        PostDto newPost = postService.addPost(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing post",
            description = "Provide its id in the url and the post object in the request body",
            tags = { "post", "put" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PostDto.class), mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostDto post) {
        PostDto newPost = postService.updatePost(id, post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a post",
            description = "Delete a post by providing its id, returns no content",
            tags = { "post", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema())})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Search posts",
            description = "Provide a keyword in the url, the API will return posts that contains the keyword in the title",
            tags = { "post", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class)), mediaType = "application/json")})
    })
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(
            @Parameter(description = "Add the keyword in the url /search?keyword={yourkeyword}, if it's empty it will return all posts") @RequestParam String keyword) {
        List<PostDto> posts = postService.searchPosts(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}