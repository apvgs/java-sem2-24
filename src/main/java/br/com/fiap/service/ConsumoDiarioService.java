package br.com.fiap.service;

import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ConsumoDiarioService {

    void cadastrar(Connection connection, ConsumoDiario consumoDiario) throws SQLException;

    List<ConsumoDiario> listar(Long idEndereco) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    ConsumoDiario buscarPorId(Long idUsuario, LocalDate date) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    void alterar(Connection connection, ConsumoDiario consumoDiario) throws SQLException, ConsumoNotFound;
}
