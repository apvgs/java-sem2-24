package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.LeituraEnergiaDao;
import br.com.fiap.dao.LeituraEnergiaDaoFactory;
import br.com.fiap.dao.NotificacaoDao;
import br.com.fiap.dao.NotificacaoDaoFactory;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;
import br.com.fiap.model.Notificacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class NotificacaoServiceImpl implements NotificacaoService {

    private NotificacaoDao notificacaoDao = NotificacaoDaoFactory.getNotificacaoDao();

    @Override
    public void inserir(Connection connection, Notificacao notificacao) throws SQLException {
        notificacaoDao.inserir(connection, notificacao);
    }


    @Override
    public List<Notificacao> listar(Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return notificacaoDao.buscar(connection, usuarioId);
        }
    }
}
