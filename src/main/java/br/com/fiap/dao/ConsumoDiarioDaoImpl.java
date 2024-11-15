package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

final class ConsumoDiarioDaoImpl implements ConsumoDiarioDao{

    @Override
    public void cadastrarConsumoDiario(Connection connection, ConsumoDiario consumoDiario) throws SQLException {
        String sql = """
                insert into T_GS_CONSUMO_DIARIO(consumo_diario, data, endereco_id)
                values(?, ?, ?)
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id_consumo"})){
            ps.setDouble(1, consumoDiario.getConsumoDiario());
            ps.setDate(2, Date.valueOf(consumoDiario.getDate()));
            ps.setLong(3, consumoDiario.getEndereco().getId());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    consumoDiario.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public ConsumoDiario buscarConsumoDiariobyId(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
                select c.*, e.* usuario.*, login.*, from T_GS_CONSUMO_DIARIO c
                join T_GS_ENDERECO e on (e.id_endereco = c.endereco_id)
                join T_GS_USUARIO usuario on usuario.id_usuario = e.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where id_consumo = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return InstanciaObjetos.instanciaConsumoDiario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<ConsumoDiario> buscarConsumoDiarioByEnderecoId(Connection connection, Long enderecoId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
                select c.*, e.* usuario.*, login.* from T_GS_CONSUMO_DIARIO c
                join T_GS_ENDERECO e on (e.id_endereco = c.endereco_id)
                join T_GS_USUARIO usuario on usuario.id_usuario = e.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where e.id_endereco = ?
                """;
        List<ConsumoDiario> lista = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, enderecoId);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    lista.add(InstanciaObjetos.instanciaConsumoDiario(rs));
                }
            }
        }
        return lista;
    }
}
