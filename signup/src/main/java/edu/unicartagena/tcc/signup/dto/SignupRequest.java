package edu.unicartagena.tcc.signup.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    private String nombreCompleto;

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    private String telefono;

    @NotBlank
    private String direccion;

    @NotBlank
    private String identificacion;

    @Size(min = 6)
    private String contrasena;
}
