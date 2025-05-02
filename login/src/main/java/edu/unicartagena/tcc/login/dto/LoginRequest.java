package edu.unicartagena.tcc.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String contrasena;
}