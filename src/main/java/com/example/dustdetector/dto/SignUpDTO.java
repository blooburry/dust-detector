package com.example.dustdetector.dto;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Validated
@Data
public class SignUpDTO {
    @NotBlank(message = "Gebruikersnaam kan niet leeg worden gelaten.")
    private String username;

    @NotBlank(message = "Wachtwoord kan niet leeg worden gelaten.")
    private String password;

    @NotBlank(message = "Email kan niet leeg worden gelaten.")
    private String email;

    @NotBlank(message = "Telefoonnummer kan niet leeg worden gelaten.")
    private String phoneNumber;
}
