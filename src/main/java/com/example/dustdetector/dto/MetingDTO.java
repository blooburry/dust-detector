package com.example.dustdetector.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Validated
@Data
public class MetingDTO {
    @NotBlank
    private Integer detectorId;

    @NotBlank
    private Integer stofniveau;
}
