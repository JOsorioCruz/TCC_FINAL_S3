package edu.unicartagena.tcc.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED) // Devuelve código 401
public class UsuarioOCredencialesInvalidasException extends RuntimeException {

    public UsuarioOCredencialesInvalidasException() {
        super("Usuario o contraseña inválida.");
    }

    public UsuarioOCredencialesInvalidasException(String message) {
        super(message);
    }
}
