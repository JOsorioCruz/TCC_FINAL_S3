package edu.unicartagena.tcc.signup.service.impl;

import edu.unicartagena.tcc.signup.dto.SignupRequest;
import edu.unicartagena.tcc.signup.entity.Usuario;
import edu.unicartagena.tcc.signup.repository.UsuarioRepository;
import edu.unicartagena.tcc.signup.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registrarUsuario(SignupRequest request) {

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        if (usuarioRepository.existsByIdentificacion(request.getIdentificacion())) {
            throw new IllegalArgumentException("La identificación ya está registrada en el sistema.");
        }

        if (usuarioRepository.existsByTelefono(request.getTelefono())) {
            throw new IllegalArgumentException("El número de teléfono ya está registrado en el sistema.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombreCompleto(request.getNombreCompleto())
                .correo(request.getCorreo())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .identificacion(request.getIdentificacion())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .build();

        usuarioRepository.save(nuevoUsuario);
    }
}