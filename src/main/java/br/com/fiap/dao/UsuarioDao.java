package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.UsuarioNotFound;
import br.com.fiap.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;


public interface UsuarioDao {

    void inserir(Connection connection, Usuario usuario) throws SQLException;
    void alterar(Connection connection, Usuario usuario) throws SQLException, UsuarioNotFound;
    Usuario buscarPorLoginId(Connection connection, Long loginId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
