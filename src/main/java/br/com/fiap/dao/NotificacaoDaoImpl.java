package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Notificacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

final class NotificacaoDaoImpl implements NotificacaoDao {

    @Override
    public void inserir(Connection connection, Notificacao notificacao) throws SQLException {
        String sql = """
                insert into T_GS_NOTIFICACAO(mensagem, data, leitura_energia_id)
                values (?, ?, ?)
               """;
        try(PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id_notificacao"})) {
            ps.setString(1, notificacao.getMensagem());
            ps.setTimestamp(2, Timestamp.valueOf(notificacao.getDataCriacao()));
            ps.setLong(3, notificacao.getLeituraEnergia().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    notificacao.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public List<Notificacao> buscar(Connection connection, Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        String sql = """
               select n.*, l.*, d.*, usuario.*, login.* from T_GS_NOTIFICACAO n 
                join T_GS_LEITURA_ENERGIA l on n.leitura_energia_id = l.id_leitura
                join T_GS_DISPOSITIVO_MEDICAO d on (d.id_dispositivo = l.dispositivo_id)
                join T_GS_USUARIO usuario on usuario.id_usuario = d.usuario_id
                join T_GS_LOGIN login on login.id_login = usuario.login_id
                where usuario.id_usuario = ?
               """;
        List<Notificacao> lista = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    lista.add(InstanciaObjetos.instanciaNotificacao(rs));
                }
            }
        }
        return lista;
    }
}
