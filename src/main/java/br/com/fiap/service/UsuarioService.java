package br.com.fiap.service;

import br.com.fiap.dto.DashBoardDto;
import br.com.fiap.exception.*;
import br.com.fiap.model.Usuario;

import java.sql.SQLException;

public interface UsuarioService {

    void cadastrarUsuario(Usuario usuario) throws SQLException, ErroAoCriarLogin, EmailJaExistente;
    void alterarUsuario(Usuario usuario) throws SQLException, UsuarioNotFound;
    Usuario buscarUsuario(String email) throws SQLException, ErroAoCriarLogin, CpfInvalido, LoginNotFound;
    DashBoardDto dashBoard(String email) throws SQLException, ErroAoCriarLogin, LoginNotFound, CpfInvalido;

}
