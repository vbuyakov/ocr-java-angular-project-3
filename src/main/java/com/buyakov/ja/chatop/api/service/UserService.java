package com.buyakov.ja.chatop.api.service;

import com.buyakov.ja.chatop.api.dto.LoginUserDto;
import com.buyakov.ja.chatop.api.dto.RegisterUserDto;
import com.buyakov.ja.chatop.api.dto.UserInfoResponse;
import com.buyakov.ja.chatop.api.exception.AuthException;
import com.buyakov.ja.chatop.api.exception.ResourceNotFoundException;
import com.buyakov.ja.chatop.api.mapper.UserMapper;
import com.buyakov.ja.chatop.api.model.User;
import com.buyakov.ja.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    /**
     * Converts RegisterUserDto to User entity and encodes the password.
     * This is the proper place for business logic like password encoding.
     */
    public User registerUser(RegisterUserDto registerUserDto) {
        User user = new User()
                .setEmail(registerUserDto.getEmail())
                .setName(registerUserDto.getName())
                .setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        return userRepository.save(user);
    }

    public User loginUser(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );
        return  userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new AuthException("Invalid email or password"));
    }

    /**
     * Checks if a user with the given email exists.
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toUserInfoResponse(user);
    }
}

