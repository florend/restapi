package com.florend.restapi.controller;

import com.florend.restapi.dto.LoginDto;
import com.florend.restapi.dto.RegisterDto;
import com.florend.restapi.payload.LoginResponse;
import com.florend.restapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto user) {
        String result = authService.register(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto user) {
        LoginResponse result = authService.login(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
