package edu.unicartagena.tcc.signup.controller;

import edu.unicartagena.tcc.signup.dto.SignupRequest;
import edu.unicartagena.tcc.signup.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignupController {

    private final UsuarioService usuarioService;

    @PostMapping("/signup")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid SignupRequest request) {
        usuarioService.registrarUsuario(request);
        return ResponseEntity.ok("Usuario registrado correctamente.");
    }
}