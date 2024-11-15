package br.com.fiap.service;

import br.com.fiap.model.Login;

import java.time.Instant;

public interface TokenService {

    String genToken(Login login);

    String getSubject(String token);

    Instant expirationDate();

}
