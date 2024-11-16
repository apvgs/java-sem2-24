package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface LeituraEnergiaDao {

    void inserir(Connection connection, LeituraEnergia leituraEnergia) throws SQLException;

    LeituraEnergia buscar(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<LeituraEnergia> listar(Connection connection, Long enderecoId) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<LeituraEnergia> listar(Connection connection, Long usuarioId, LocalDate data) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
