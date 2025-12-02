package com.buyakov.ja.chatop.api.dto;

import com.buyakov.ja.chatop.api.dto.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalDto {
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    private Double surface;

    @NotBlank
    private Double price;

    @NotNull(groups = OnCreate.class)
    private MultipartFile picture;

    @NotBlank
    @Size(min = 1, max = 2000)
    private String description;
}
