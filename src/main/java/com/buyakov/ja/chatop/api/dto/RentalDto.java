package com.buyakov.ja.chatop.api.dto;

import com.buyakov.ja.chatop.api.dto.validation.OnCreate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalDto {
    @NotNull
    private String name;

    @NotNull
    private Double surface;

    @NotNull
    private Double price;

    @NotNull(groups = OnCreate.class)
    private MultipartFile picture;

    @NotNull
    private String description;
}
