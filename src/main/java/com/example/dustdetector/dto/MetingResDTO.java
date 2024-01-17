package com.example.dustdetector.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetingResDTO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private int dustLevel;
    private int detectorId;
}
