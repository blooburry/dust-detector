package com.example.dustdetector.dto;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@Validated
public class SignUpDTO {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
