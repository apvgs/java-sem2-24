package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.EmailJaExistente;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.UsuarioNotFound;
import br.com.fiap.model.Usuario;

import java.sql.SQLException;

public interface UsuarioService {

    void cadastrarUsuario(Usuario usuario) throws SQLException, ErroAoCriarLogin, EmailJaExistente;
    void alterarUsuario(Usuario usuario) throws SQLException, UsuarioNotFound;
    Usuario buscarUsuario(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;

}
