package com.buyakov.ja.chatop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String email;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Password is required")
    private String password;
}
