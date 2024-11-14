package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ConsumoDiarioDao {

    void cadastrarConsumoDiario(Connection connection, ConsumoDiario consumoDiario) throws SQLException;

    ConsumoDiario buscarConsumoDiariobyId(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<ConsumoDiario> buscarConsumoDiarioByEnderecoId(Connection connection, Long enderecoId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
