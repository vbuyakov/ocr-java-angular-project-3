package com.buyakov.ja.chatop.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {
    @NotEmpty(message = "username is required")
    @Size(min = 1, max = 255)
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 1, max = 255)
    private String password;
}
