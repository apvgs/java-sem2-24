package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Endereco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface EnderecoDao {

    void cadastrar(Connection connection, Endereco endereco) throws SQLException;
    Endereco getEndereco(Connection connection, Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;
    List<Endereco> getEnderecosByUsuaroId(Connection connection, Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
