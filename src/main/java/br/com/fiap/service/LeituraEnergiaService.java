package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;

import java.sql.SQLException;
import java.util.List;

public interface LeituraEnergiaService {

    void inserir(LeituraEnergia leituraEnergia) throws SQLException;

    LeituraEnergia buscar(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<LeituraEnergia> listar(Long leituraEnergiaId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
