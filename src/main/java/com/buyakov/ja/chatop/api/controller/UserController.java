package com.buyakov.ja.chatop.api.controller;

import com.buyakov.ja.chatop.api.dto.UserInfoResponse;
import com.buyakov.ja.chatop.api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Tag(name = "User Information")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserInfo(id));
    }
}
