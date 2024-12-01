package com.florend.restapi.controller;

import com.florend.restapi.dto.LoginDto;
import com.florend.restapi.dto.RegisterDto;
import com.florend.restapi.model.Users;
import com.florend.restapi.payload.LoginResponse;
import com.florend.restapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "All authentication related APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Register a user",
            description = "",
            tags = { "register" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")})
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto user) {
        String result = authService.register(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login",
            description = "Authenticate by sending user credentials : username and password",
            tags = { "login" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")})
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto user) {
        LoginResponse result = authService.login(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(
            summary = "Get current user",
            description = "Retrieve authenticated user info",
            tags = { "user" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Users.class), mediaType = "application/json")})
    })
    @GetMapping("/me")
    public ResponseEntity<Users> getCurrentUser() {
        Users user = authService.getCurrentUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
