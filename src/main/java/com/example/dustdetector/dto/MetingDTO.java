package com.example.dustdetector.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Validated
@Data
public class MetingDTO {
    @NotNull
    private Integer detectorId;

    @NotNull
    private Integer stofniveau;
}
