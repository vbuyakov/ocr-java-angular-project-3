package com.buyakov.ja.chatop.api.controller;

import com.buyakov.ja.chatop.api.dto.LoginResponse;
import com.buyakov.ja.chatop.api.dto.LoginUserDto;
import com.buyakov.ja.chatop.api.dto.RegisterUserDto;
import com.buyakov.ja.chatop.api.dto.UserInfoResponse;
import com.buyakov.ja.chatop.api.exception.AuthException;
import com.buyakov.ja.chatop.api.exception.DataConflictException;
import com.buyakov.ja.chatop.api.mapper.UserMapper;
import com.buyakov.ja.chatop.api.model.User;
import com.buyakov.ja.chatop.api.service.JwtService;
import com.buyakov.ja.chatop.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@Tag(name = "Authentification")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        if(userService.existsByEmail(registerUserDto.getEmail())) {
            throw new DataConflictException("Email already in use");
        }
        userService.registerUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDto loginUserDto) {
        try {
            // loginUser will throw BadCredentialsException if authentication fails
            User user = userService.loginUser(loginUserDto);
            // User implements UserDetails, so we can use it directly for JWT generation
            String jwtToken = jwtService.generateToken(user);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException | AuthenticationCredentialsNotFoundException e) {
            throw new AuthException("Invalid email or password");
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Protected endpoint - requires authentication
    @Operation(
            summary = "Get current authenticated user info",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<UserInfoResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userMapper.toUserInfoResponse(currentUser));
    }
}
