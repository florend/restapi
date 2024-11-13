package com.florend.restapi.service;

import com.florend.restapi.dto.LoginDto;
import com.florend.restapi.dto.RegisterDto;
import com.florend.restapi.payload.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
