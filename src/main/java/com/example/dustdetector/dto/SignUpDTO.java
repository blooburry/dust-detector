package com.example.dustdetector.dto;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.*;
import lombok.Data;

@Validated
@Data
public class SignUpDTO {
    @NotBlank(message = "Gebruikersnaam kan niet leeg worden gelaten.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "Username must be alphanumeric and between 4 and 16 characters")
    private String username;

    @NotBlank(message = "Wachtwoord kan niet leeg worden gelaten.")
    private String password;

    @NotBlank(message = "Email kan niet leeg worden gelaten.")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Telefoonnummer kan niet leeg worden gelaten.")
    private String phoneNumber;
}
