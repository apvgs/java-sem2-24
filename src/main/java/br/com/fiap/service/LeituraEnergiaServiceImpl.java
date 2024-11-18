package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.LeituraEnergiaDao;
import br.com.fiap.dao.LeituraEnergiaDaoFactory;
import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.LeituraEnergia;
import br.com.fiap.model.Notificacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

final class LeituraEnergiaServiceImpl implements LeituraEnergiaService {

    private LeituraEnergiaDao leituraEnergiaDao = LeituraEnergiaDaoFactory.getLeituraEnergiaDao();
    private ConsumoDiarioService consumoDiarioService = ConsumoDiarioServiceFactory.create();
    private NotificacaoService notificacaoService = NotificacaoServiceFactory.create();

    @Override
    public void inserir(LeituraEnergia leituraEnergia) throws SQLException, ErroAoCriarLogin, CpfInvalido, ConsumoNotFound {
        ConsumoDiario consumoDiario = consumoDiarioService.buscarPorId(leituraEnergia.getDispositivoMedicao().getEndereco().getUsuario().getId(),
                leituraEnergia.getDataMedicao().toLocalDate());
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            leituraEnergiaDao.inserir(connection, leituraEnergia);
            if (consumoDiario == null){
                consumoDiario = new ConsumoDiario();
                consumoDiario.setEndereco(leituraEnergia.getDispositivoMedicao().getEndereco());
                consumoDiario.setDate(leituraEnergia.getDataMedicao().toLocalDate());
                consumoDiario.setConsumoDiario(leituraEnergia.getConsumo());
                consumoDiarioService.cadastrar(connection, consumoDiario);
            }
            consumoDiario.setConsumoDiario(consumoDiario.getConsumoDiario() + leituraEnergia.getConsumo());
            consumoDiarioService.alterar(connection, consumoDiario);
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
    public List<LeituraEnergia> listar(Long usuarioId, LocalDate data) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return leituraEnergiaDao.listar(connection, usuarioId, data);
        }
    }
}
