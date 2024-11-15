package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DispositivoMedicaoDao {

    void inserir(Connection connection, DispositivoMedicao dispositivoMedicao) throws SQLException;

    List<DispositivoMedicao> listar(Connection connection, Long enderecoId) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    DispositivoMedicao buscar(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
