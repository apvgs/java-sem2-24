package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Notificacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface NotificacaoDao {

    void inserir(Connection connection, Notificacao notificacao) throws SQLException;

    List<Notificacao> buscar(Connection connection, Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
