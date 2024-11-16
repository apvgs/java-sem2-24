package br.com.fiap.service;

import br.com.fiap.exception.EmailJaExistente;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginInvalido;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.Login;

import java.sql.Connection;
import java.sql.SQLException;

public interface LoginService {

    void create(Login login, Connection connection) throws SQLException, EmailJaExistente, ErroAoCriarLogin;

    Login logarUsuario(String email, String senha) throws SQLException, LoginInvalido, LoginNotFound, ErroAoCriarLogin;

    Login findByEmail(String email) throws SQLException, ErroAoCriarLogin, LoginNotFound;

}
