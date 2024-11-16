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

        try(PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id_usuario"})) {
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
    public Usuario buscarPorLoginId(Connection connection, Long loginId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
            select usuario.*, login.* from T_GS_USUARIO usuario
            join T_GS_LOGIN login on usuario.login_id = login.id_login
            where usuario.login_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return InstanciaObjetos.instanciaUsuario(rs);
                }
            }
        }
        return null;
    }

}
