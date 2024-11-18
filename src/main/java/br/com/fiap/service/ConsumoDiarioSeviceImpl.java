package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.ConsumoDiarioDao;
import br.com.fiap.dao.ConsumoDiarioDaoFactory;
import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

final class ConsumoDiarioSeviceImpl implements ConsumoDiarioService{

    private ConsumoDiarioDao consumoDiarioDao = ConsumoDiarioDaoFactory.getConsumoDiarioDao();

    @Override
    public void cadastrar(Connection connection, ConsumoDiario consumoDiario) throws SQLException {
        consumoDiarioDao.cadastrarConsumoDiario(connection, consumoDiario);
    }

    @Override
    public List<ConsumoDiario> listar(Long idUsuario) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return consumoDiarioDao.buscarConsumoDiarioByUsuarioId(connection, idUsuario);
        }
    }

    @Override
    public ConsumoDiario buscarPorId(Long idUsuario, LocalDate date) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return consumoDiarioDao.buscarConsumoDiario(connection, idUsuario, date);
        }
    }

    @Override
    public void alterar(Connection connection, ConsumoDiario consumoDiario) throws SQLException, ConsumoNotFound {
        consumoDiarioDao.alterar(connection, consumoDiario);
    }
}
