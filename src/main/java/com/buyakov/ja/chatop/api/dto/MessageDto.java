package com.buyakov.ja.chatop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MessageDto {
    @NotBlank
    private String message;
    @NotEmpty
    private Long rental_id;
    @NotEmpty
    private Long user_id;

    public Long getRentalId() {
        return rental_id;
    }

    public Long getUserId() {
        return user_id;
    }
}
