package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.UsuarioNotFound;
import br.com.fiap.model.Login;
import br.com.fiap.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

final class UsuarioDaoImpl implements UsuarioDao {

    @Override
    public void inserir(Connection connection, Usuario usuario) throws SQLException {
        String sql = """
                insert into T_GS_USUARIO(login_id, cpf, nome)
                values (?, ?, ?)
            """;

        try(PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"})) {
            ps.setLong(1, usuario.getLogin().getId());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getNome());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
        }

    }

    @Override
    public void alterar(Connection connection, Usuario usuario) throws SQLException, UsuarioNotFound {
        String sql = """
                update T_PS_USUARIO set nome = ?, cpf = ? where cd_pessoa = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setLong(3, usuario.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new UsuarioNotFound();
            }
        }
    }

    @Override
    public Usuario buscarPorLoginId(Connection connection, int loginId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
            select usuario.*, login.email, login.senha, login.id as login_id from T_GS_USUARIO usuario
            join T_GS_LOGIN login on usuario.login_id = login.login_id
            where usuario.login_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return instaciaUsuario(rs);
                }
            }
        }
        return null;
    }

    private Login instaciaLogin(ResultSet rs) throws SQLException, ErroAoCriarLogin {
        Login login = new Login(rs.getString("email"), rs.getString("senha"));
        login.setId(rs.getLong("login_id"));
        return login;
    }

    private Usuario instaciaUsuario(ResultSet rs) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        Usuario usuario = new Usuario(rs.getString("cpf"), rs.getString("nome"), instaciaLogin(rs));
        usuario.setId(rs.getLong("id"));
        return usuario;
    }
}