package br.com.fiap.dao;

import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

final class ConsumoDiarioDaoImpl implements ConsumoDiarioDao{

    @Override
    public void cadastrarConsumoDiario(Connection connection, ConsumoDiario consumoDiario) throws SQLException {
        String sql = """
                insert into T_GS_CONSUMO_DIARIO(consumo_diario, data, usuario_id)
                values(?, ?, ?)
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_consumo"})){
            ps.setDouble(1, consumoDiario.getConsumoDiario());
            ps.setDate(2, Date.valueOf(consumoDiario.getDate()));
            ps.setLong(3, consumoDiario.getUsuario().getId());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    consumoDiario.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public ConsumoDiario buscarConsumoDiario(Connection connection, Long idUsuario, LocalDate data) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
                select c.*, usuario.*, login.* from T_GS_CONSUMO_DIARIO c
                join T_GS_USUARIO usuario on usuario.id_usuario = c.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where usuario.id_usuario = ? and c.data = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setDate(2, Date.valueOf(data));
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return InstanciaObjetos.instanciaConsumoDiario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<ConsumoDiario> buscarConsumoDiarioByUsuarioId(Connection connection, Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
                select c.*, usuario.*, login.* from T_GS_CONSUMO_DIARIO c
                join T_GS_USUARIO usuario on usuario.id_usuario = c.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where usuario.id_usuario = ?
                """;
        List<ConsumoDiario> lista = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    lista.add(InstanciaObjetos.instanciaConsumoDiario(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<ConsumoDiario> buscarConsumoDiarioByUsuarioIdEMes(Connection connection, Long usuarioId, int mes, int ano) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
            select c.*, usuario.*, login.* 
            from T_GS_CONSUMO_DIARIO c
            join T_GS_USUARIO usuario on usuario.id_usuario = c.usuario_id
            join T_GS_LOGIN login on login.id_login = usuario.login_id
            where usuario.id_usuario = ?
            and EXTRACT(MONTH FROM c.data) = ?
            and EXTRACT(YEAR FROM c.data) = ?
            order by c.data asc
            """;
        List<ConsumoDiario> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            ps.setInt(2, mes);
            ps.setInt(3, ano);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(InstanciaObjetos.instanciaConsumoDiario(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public void alterar(Connection connection, ConsumoDiario consumoDiario) throws SQLException, ConsumoNotFound {
        String sql = """
                update T_GS_CONSUMO_DIARIO set consumo_diario = ? where id_consumo = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, consumoDiario.getConsumoDiario());
            ps.setLong(2, consumoDiario.getId());
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected != 1){
                throw new ConsumoNotFound();
            }

        }

    }
}
