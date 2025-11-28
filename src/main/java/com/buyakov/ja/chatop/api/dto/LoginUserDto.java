package com.buyakov.ja.chatop.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginUserDto {
    @NotEmpty(message = "username is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;
}
