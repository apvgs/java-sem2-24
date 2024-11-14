package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.LoginDao;
import br.com.fiap.dao.LoginDaoFactory;
import br.com.fiap.exception.EmailJaExistente;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginInvalido;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.Login;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

final class LoginServiceImpl implements LoginService {

    private LoginDao dao = LoginDaoFactory.getLoginDao();

    @Override
    public void create(Login login, Connection connection) throws SQLException, EmailJaExistente, ErroAoCriarLogin {
        try {
            dao.findByEmail(connection, login.getEmail()).orElseThrow(LoginNotFound::new);
            throw new EmailJaExistente("Email já cadastrado. Tente outro email.");
        } catch (LoginNotFound e) {
            String senhaEncriptada = BCrypt.hashpw(login.getSenha(), BCrypt.gensalt());
            login.setSenha(senhaEncriptada);
            dao.cadastrar(connection, login);
        }
    }

    @Override
    public Login logarUsuario(String email, String senha) throws SQLException, LoginInvalido, LoginNotFound, ErroAoCriarLogin {
        try (Connection connection = DatabaseConnectionFactory.getConnection()){
            Optional<Login> usuario = dao.findByEmail(connection, email);
            if (usuario.isPresent() && BCrypt.checkpw(senha, usuario.get().getSenha()))
                return usuario.get();
            throw new LoginInvalido("Login ou senha inválidos. Verifique suas credênciais.");
        }
    }
}
