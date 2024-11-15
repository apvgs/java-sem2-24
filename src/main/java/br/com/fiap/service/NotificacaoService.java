package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;
import br.com.fiap.model.Notificacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface NotificacaoService {

    void inserir(Connection connection, Notificacao notificacao) throws SQLException;

    List<Notificacao> listar(Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
