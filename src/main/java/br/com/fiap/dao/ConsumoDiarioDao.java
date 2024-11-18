package br.com.fiap.dao;

import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ConsumoDiarioDao {

    void cadastrarConsumoDiario(Connection connection, ConsumoDiario consumoDiario) throws SQLException;

    ConsumoDiario buscarConsumoDiario(Connection connection, Long idUsuario, LocalDate data) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<ConsumoDiario> buscarConsumoDiarioByUsuarioId(Connection connection, Long usuarioId) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    void alterar(Connection connection, ConsumoDiario consumoDiario) throws SQLException, ConsumoNotFound;
}
