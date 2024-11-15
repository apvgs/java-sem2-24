package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.UsuarioDao;
import br.com.fiap.dao.UsuarioDaoFactory;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.EmailJaExistente;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.UsuarioNotFound;
import br.com.fiap.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

final class UsuarioServiceImpl implements UsuarioService {

    UsuarioDao usuarioDao = UsuarioDaoFactory.getUsuarioDao();
    LoginService loginService = LoginServiceFactory.create();

    @Override
    public void cadastrarUsuario(Usuario usuario) throws SQLException, ErroAoCriarLogin, EmailJaExistente {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            loginService.create(usuario.getLogin(), connection);
            usuarioDao.inserir(connection, usuario);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public void alterarUsuario(Usuario usuario) throws SQLException, UsuarioNotFound {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            usuarioDao.alterar(connection, usuario);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }
    }

    @Override
    public Usuario buscarUsuario(Long loginId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        Connection connection = DatabaseConnectionFactory.getConnection();
        return usuarioDao.buscarPorLoginId(connection, loginId);
    }
}
