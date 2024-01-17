package com.example.dustdetector.dto;

import java.util.Date;

import lombok.Data;

public class DetectorsResDTO {
    public String ipAddress;
    public Date dateBought;
    public int status;

    public DetectorsResDTO(String ipAddress, Date dateBought, int status) {
        this.ipAddress = ipAddress;
        this.dateBought = dateBought;
        this.status = status;
    }
}
