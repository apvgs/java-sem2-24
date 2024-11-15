package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Endereco;

import java.sql.SQLException;
import java.util.List;

public interface EnderecoService {

    void cadastrarEndereco(Endereco endereco) throws SQLException;

    List<Endereco> listarEndereco(Long idUsuario) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    Endereco buscarEndereco(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
