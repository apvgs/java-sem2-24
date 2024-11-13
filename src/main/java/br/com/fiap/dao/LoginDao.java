package br.com.fiap.dao;

import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface LoginDao {

    void cadastrar(Connection connection, Login login) throws SQLException;
    void alterar(Connection connection, Login login) throws SQLException, LoginNotFound;
    Optional<Login> findByEmail(Connection connection, String email) throws SQLException, ErroAoCriarLogin, LoginNotFound;
}
