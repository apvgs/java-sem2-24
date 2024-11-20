package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.ConsumoDiarioDao;
import br.com.fiap.dao.ConsumoDiarioDaoFactory;
import br.com.fiap.dto.ConsumoDiarioDto;
import br.com.fiap.dto.ConsumoMesDto;
import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public List<ConsumoDiario> buscarConsumoDiarioByUsuarioIdEMes(Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            int mes = LocalDate.now().getMonthValue();
            int ano = LocalDate.now().getYear();
            return consumoDiarioDao.buscarConsumoDiarioByUsuarioIdEMes(connection, usuarioId, mes, ano);
        }
    }

    @Override
    public ConsumoMesDto getConsumoMes(Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        List<ConsumoDiario> consumoDiarios = buscarConsumoDiarioByUsuarioIdEMes(usuarioId);
        double consumoMes = consumoDiarios.stream().map(ConsumoDiario::getConsumoDiario).reduce(Double::sum).orElse(0.0);
        List<ConsumoDiarioDto> consumoDiarioDtos = consumoDiarios
                .stream()
                .map(consumoDiario ->
                        new ConsumoDiarioDto(consumoDiario.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), consumoDiario.getConsumoDiario()))
                .toList();
        return new ConsumoMesDto(consumoDiarioDtos, consumoMes);
    }

    @Override
    public void alterar(Connection connection, ConsumoDiario consumoDiario) throws SQLException, ConsumoNotFound {
        consumoDiarioDao.alterar(connection, consumoDiario);
    }
}
