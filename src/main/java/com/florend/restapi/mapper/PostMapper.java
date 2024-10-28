package com.florend.restapi.mapper;

import com.florend.restapi.dto.PostDto;
import com.florend.restapi.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto postDto);
}
