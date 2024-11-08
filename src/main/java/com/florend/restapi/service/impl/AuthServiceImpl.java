package com.florend.restapi.service.impl;

import com.florend.restapi.dto.LoginDto;
import com.florend.restapi.dto.RegisterDto;
import com.florend.restapi.mapper.UserMapper;
import com.florend.restapi.model.Users;
import com.florend.restapi.repository.UserRepository;
import com.florend.restapi.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    final UserRepository userRepository;

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String login(LoginDto loginDto) {

        return "not implemented";
    }

    @Override
    public String register(RegisterDto registerDto) {
        Users newUser = UserMapper.INSTANCE.registerDtoToUsers(registerDto);
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(newUser);
        return "registered " + newUser.getEmail();
    }
}
