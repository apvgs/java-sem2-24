package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ConsumoDiarioService {

    void cadastrar(Connection connection, ConsumoDiario consumoDiario) throws SQLException;

    List<ConsumoDiario> listar(Long idEndereco) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    ConsumoDiario buscarPorId(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
