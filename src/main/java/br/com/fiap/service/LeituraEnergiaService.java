package br.com.fiap.service;

import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.LeituraEnergia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface LeituraEnergiaService {

    void inserir(LeituraEnergia leituraEnergia) throws SQLException, ErroAoCriarLogin, CpfInvalido, ConsumoNotFound;

    LeituraEnergia buscar(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<LeituraEnergia> listar(Long usuarioId, LocalDate data) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
