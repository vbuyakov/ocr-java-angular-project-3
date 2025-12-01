package com.buyakov.ja.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MessageDto {
    @NotBlank
    private String message;
    @JsonProperty("rental_id")
    private Long rentalId;
    @JsonProperty("user_id")
    private Long userId;
}
