package com.buyakov.ja.chatop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @Size(min = 1, max = 255)
    private String email;

    @NotEmpty(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotEmpty(message = "Password is required")
    @Size(min = 1, max = 255)
    private String password;
}
