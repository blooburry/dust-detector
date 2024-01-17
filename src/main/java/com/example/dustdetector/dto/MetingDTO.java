package com.example.dustdetector.dto;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
