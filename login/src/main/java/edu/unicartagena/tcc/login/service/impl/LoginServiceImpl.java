package edu.unicartagena.tcc.login.service.impl;

import edu.unicartagena.tcc.login.dto.LoginRequest;
import edu.unicartagena.tcc.login.dto.LoginResponse;
import edu.unicartagena.tcc.login.entity.Usuario;
import edu.unicartagena.tcc.login.repository.UsuarioRepository;
import edu.unicartagena.tcc.login.security.JwtService;
import edu.unicartagena.tcc.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        // Buscar el usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Comparar la contraseña en texto plano con la encriptada en la base de datos
        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar token JWT y retornarlo
        String token = jwtService.generarToken(usuario.getCorreo());
        return new LoginResponse(token);
    }
}
