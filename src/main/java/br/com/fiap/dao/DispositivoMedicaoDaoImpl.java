package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class DispositivoMedicaoDaoImpl implements DispositivoMedicaoDao {

    @Override
    public void inserir(Connection connection, DispositivoMedicao dispositivoMedicao) throws SQLException {
        String sql = """
                insert into T_GS_DISPOSITIVO_MEDICAO(nome, status, localizacao, usuario_id)
                values (?, ?, ?, ?)
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id_dispositivo"})) {
            ps.setString(1, dispositivoMedicao.getCodigo());
            ps.setString(2, dispositivoMedicao.getStatus().toString());
            ps.setString(3, dispositivoMedicao.getLocalizacao());
            ps.setLong(4, dispositivoMedicao.getUsuario().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dispositivoMedicao.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public DispositivoMedicao buscar(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
               select d.*, usuario.*, login.* from T_GS_DISPOSITIVO_MEDICAO d
                join T_GS_USUARIO usuario on usuario.id_usuario = d.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where d.id_dispositivo = ?
               """;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return InstanciaObjetos.instanciaDispositivoMedicao(rs);
                }
            }

        }
        return null;
    }

    @Override
    public List<DispositivoMedicao> buscarByUsuarioId(Connection connection, Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
               select d.*, usuario.*, login.* from T_GS_DISPOSITIVO_MEDICAO d
                join T_GS_USUARIO usuario on usuario.id_usuario = d.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where d.usuario_id = ?
               """;
        List<DispositivoMedicao> dispositivoMedicao = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dispositivoMedicao.add(InstanciaObjetos.instanciaDispositivoMedicao(rs));
                }
            }

        }
        return dispositivoMedicao;
    }
}
