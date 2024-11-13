package com.florend.restapi.service.impl;

import com.florend.restapi.dto.LoginDto;
import com.florend.restapi.dto.RegisterDto;
import com.florend.restapi.jwt.JwtService;
import com.florend.restapi.mapper.UserMapper;
import com.florend.restapi.model.Users;
import com.florend.restapi.payload.LoginResponse;
import com.florend.restapi.repository.UserRepository;
import com.florend.restapi.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    final UserRepository userRepository;

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userRepository.findByUsername(loginDto.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(authentication));
        loginResponse.setUser(user);
        return loginResponse;
    }

    @Override
    public String register(RegisterDto registerDto) {
        Users newUser = UserMapper.INSTANCE.registerDtoToUsers(registerDto);
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(newUser);
        return "registered " + newUser.getEmail();
    }
}
