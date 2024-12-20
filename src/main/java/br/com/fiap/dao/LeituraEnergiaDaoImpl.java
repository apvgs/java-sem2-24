package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

final class LeituraEnergiaDaoImpl implements LeituraEnergiaDao {

    @Override
    public void inserir(Connection connection, LeituraEnergia leituraEnergia) throws SQLException {
        String sql = """
                insert into T_GS_LEITURA_ENERGIA(dt_medicao, consumo, dispositivo_id)
                values (?, ?, ?)
               """;
        try(PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id_leitura"})) {
            ps.setTimestamp(1, Timestamp.valueOf(leituraEnergia.getDataMedicao()));
            ps.setDouble(2, leituraEnergia.getConsumo());
            ps.setLong(3, leituraEnergia.getDispositivoMedicao().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    leituraEnergia.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public LeituraEnergia buscar(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
               select l.*, d.*, usuario.*, login.* from T_GS_LEITURA_ENERGIA l
                join T_GS_DISPOSITIVO_MEDICAO d on (d.id_dispositivo = l.dispositivo_id)
                join T_GS_USUARIO usuario on usuario.id_usuario = d.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where l.id_leitura = ?
               """;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return InstanciaObjetos.instanciaLeitura(rs);
                }
            }

        }
        return null;
    }

    @Override
    public List<LeituraEnergia> listar(Connection connection, Long usuarioId, LocalDate data) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
               select l.*, d.*, usuario.*, login.* from T_GS_LEITURA_ENERGIA l
                join T_GS_DISPOSITIVO_MEDICAO d on (d.id_dispositivo = l.dispositivo_id)
                join T_GS_USUARIO usuario on usuario.id_usuario = d.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where usuario.id_usuario = ? and trunc(dt_medicao) = ?
               """;
        List<LeituraEnergia> lista = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            ps.setDate(2, Date.valueOf(data));
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    lista.add(InstanciaObjetos.instanciaLeitura(rs));
                }
            }
        }
        return lista;
    }
}
