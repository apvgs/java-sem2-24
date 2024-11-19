package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.DispositivoNotFound;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DispositivoMedicaoDao {

    void inserir(Connection connection, DispositivoMedicao dispositivoMedicao) throws SQLException;

    DispositivoMedicao buscar(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<DispositivoMedicao> buscarByUsuarioId(Connection connection, Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    void alterarDispositivo(Connection connection, String localizacao, Long id) throws SQLException, DispositivoNotFound;

    void excluirDispositivo(Connection connection, Long id) throws SQLException, DispositivoNotFound;
}
