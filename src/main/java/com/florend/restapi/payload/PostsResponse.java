package com.florend.restapi.payload;

import com.florend.restapi.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsResponse {
    private long totalCount;
    private List<Post> items;
    private int totalPages;
}
