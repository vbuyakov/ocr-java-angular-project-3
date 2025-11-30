package com.buyakov.ja.chatop.api.dto;

import com.buyakov.ja.chatop.api.dto.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalDto {
    @NotBlank
    private String name;

    @NotBlank
    private Double surface;

    @NotBlank
    private Double price;

    @NotNull(groups = OnCreate.class)
    private MultipartFile picture;

    @NotBlank
    private String description;
}
