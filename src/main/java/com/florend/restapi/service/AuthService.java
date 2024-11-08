package com.florend.restapi.service;

import com.florend.restapi.dto.LoginDto;
import com.florend.restapi.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
