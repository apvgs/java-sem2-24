package br.com.fiap.dao;

import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

final class LoginDaoImpl implements LoginDao {

    @Override
    public void cadastrar(Connection connection, Login login) throws SQLException {
        String sql = """ 
            insert into T_GS_LOGIN(email, senha)
            values (?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_login"})) {
            ps.setString(1, login.getEmail());
            ps.setString(2, login.getSenha());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        login.setId(rs.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public void alterar(Connection connection, Login login) throws SQLException, LoginNotFound {
        String sql = """
            update T_GS_LOGIN
            set sq_senha = ?
            where id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login.getSenha());
            ps.setLong(2, login.getId());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new LoginNotFound();
            }
        }
    }

    @Override
    public Optional<Login> findByEmail(Connection connection, String email) throws SQLException, ErroAoCriarLogin, LoginNotFound {
        String sql = """
            select * from T_PS_LOGIN where email = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(InstanciaObjetos.instanciaLogin(rs));
                } else {
                    throw new LoginNotFound();
                }
            }
        }
    }
}
