package com.buyakov.ja.chatop.api.security;

import com.buyakov.ja.chatop.api.exception.ResourceNotFoundException;
import com.buyakov.ja.chatop.api.model.User;
import com.buyakov.ja.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserProvider {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByEmail(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found" + username));
    }
}
