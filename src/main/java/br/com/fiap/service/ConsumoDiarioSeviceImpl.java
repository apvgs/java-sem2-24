package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.ConsumoDiarioDao;
import br.com.fiap.dao.ConsumoDiarioDaoFactory;
import br.com.fiap.dao.DispositivoMedicaoDao;
import br.com.fiap.dao.DispositivoMedicaoDaoFactory;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class ConsumoDiarioSeviceImpl implements ConsumoDiarioService{

    private ConsumoDiarioDao consumoDiarioDao = ConsumoDiarioDaoFactory.getConsumoDiarioDao();

    @Override
    public void cadastrar(Connection connection, ConsumoDiario consumoDiario) throws SQLException {
        consumoDiarioDao.cadastrarConsumoDiario(connection, consumoDiario);
    }

    @Override
    public List<ConsumoDiario> listar(Long idEndereco) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return consumoDiarioDao.buscarConsumoDiarioByEnderecoId(connection, idEndereco);
        }
    }

    @Override
    public ConsumoDiario buscarPorId(Long idDispositivoMedicao) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return consumoDiarioDao.buscarConsumoDiariobyId(connection, idDispositivoMedicao);
        }
    }
}
