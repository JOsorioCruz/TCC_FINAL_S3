package edu.unicartagena.tcc.login.service;

import edu.unicartagena.tcc.login.dto.LoginRequest;
import edu.unicartagena.tcc.login.dto.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest request);
}