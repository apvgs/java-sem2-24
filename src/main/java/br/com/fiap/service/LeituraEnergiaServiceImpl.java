package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.LeituraEnergiaDao;
import br.com.fiap.dao.LeituraEnergiaDaoFactory;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;
import br.com.fiap.model.Notificacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class LeituraEnergiaServiceImpl implements LeituraEnergiaService {

    private LeituraEnergiaDao leituraEnergiaDao = LeituraEnergiaDaoFactory.getLeituraEnergiaDao();
    private NotificacaoService notificacaoService = NotificacaoServiceFactory.create();

    @Override
    public void inserir(LeituraEnergia leituraEnergia) throws SQLException {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            leituraEnergiaDao.inserir(connection, leituraEnergia);
            if (leituraEnergia.getConsumo() > 700){
                Notificacao notificacao = new Notificacao("Alerta Houve um pico de energia!!!!", leituraEnergia);
                notificacaoService.inserir(connection, notificacao);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public LeituraEnergia buscar(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return leituraEnergiaDao.buscar(connection, id);
        }
    }

    @Override
    public List<LeituraEnergia> listar(Long enderecoId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return leituraEnergiaDao.listar(connection, enderecoId);
        }
    }
}
