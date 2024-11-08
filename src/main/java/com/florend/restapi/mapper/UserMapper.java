package com.florend.restapi.mapper;

import com.florend.restapi.dto.RegisterDto;
import com.florend.restapi.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    RegisterDto usersToRegisterDto(Users user);

    @Mapping(target = "id", ignore = true)
    Users registerDtoToUsers(RegisterDto registerDto);
}
