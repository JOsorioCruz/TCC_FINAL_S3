package edu.unicartagena.tcc.signup.service;

import edu.unicartagena.tcc.signup.dto.SignupRequest;

public interface UsuarioService {
    void registrarUsuario(SignupRequest request);
}
