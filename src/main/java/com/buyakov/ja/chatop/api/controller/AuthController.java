package com.buyakov.ja.chatop.api.controller;

import com.buyakov.ja.chatop.api.dto.LoginResponse;
import com.buyakov.ja.chatop.api.dto.LoginUserDto;
import com.buyakov.ja.chatop.api.dto.RegisterUserDto;
import com.buyakov.ja.chatop.api.dto.UserInfoResponse;
import com.buyakov.ja.chatop.api.model.User;
import com.buyakov.ja.chatop.api.service.JwtService;
import com.buyakov.ja.chatop.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        if(userService.existsByEmail(registerUserDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Protected endpoint - requires authentication
    public ResponseEntity<UserInfoResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        
        UserInfoResponse userInfoResponse = new UserInfoResponse()
            .setId(currentUser.getId())
            .setEmail(currentUser.getEmail())
            .setName(currentUser.getName())
            .setCreated_at(currentUser.getCreatedAt().format(formatter))
            .setUpdated_at(currentUser.getUpdatedAt().format(formatter));

        return ResponseEntity.ok(userInfoResponse);
    }
}
