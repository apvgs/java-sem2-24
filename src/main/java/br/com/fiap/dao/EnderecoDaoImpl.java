package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class EnderecoDaoImpl implements EnderecoDao {

    @Override
    public void cadastrar(Connection connection, Endereco endereco) throws SQLException {
        String sql = """
            insert into T_GS_ENDERECO (usuario_id, rua, cidade, estado, bairro, cep, numero, apelido)
            values (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cd_endereco"})) {
            ps.setLong(1, endereco.getUsuario().getId());
            ps.setString(2, endereco.getRua());
            ps.setString(3, endereco.getCidade());
            ps.setString(4, endereco.getEstado());
            ps.setString(5, endereco.getBairro());
            ps.setString(6, endereco.getCep());
            ps.setInt( 7, endereco.getNumero());
            ps.setString(8, endereco.getApelido());
            ps.executeUpdate();
            try (ResultSet   rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Endereco getEndereco(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
            select e.*, usuario.*, login.*
            from T_GS_ENDERECO e
            join T_GS_USUARIO usuario on usuario.id_usuario = e.usuario_id
            join T_GS_LOGIN login on login.id_login = usuario.login_id
            where e.id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return InstanciaObjetos.instanciaEndereco(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Endereco> getEnderecosByUsuaroId(Connection connection, Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
            select e.*, usuario.*, login.*
            from T_GS_ENDERECO e
            join T_GS_USUARIO usuario on usuario.id_usuario = e.usuario_id
            join T_GS_LOGIN login on login.id_login = usuario.login_id
            where usuario.id_usuario = ?
        """;
        List<Endereco> enderecos = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    enderecos.add(InstanciaObjetos.instanciaEndereco(rs));
                }
            }
        }
        return enderecos;
    }
}
