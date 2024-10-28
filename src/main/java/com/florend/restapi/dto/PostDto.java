package com.florend.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    private String image;
    private String title;
    private String body;
    private LocalDateTime date;
}
