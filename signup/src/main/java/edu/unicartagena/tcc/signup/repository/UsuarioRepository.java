package edu.unicartagena.tcc.signup.repository;

import edu.unicartagena.tcc.signup.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByIdentificacion(String identificacion);
    boolean existsByTelefono(String telefono);
}