package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.EnderecoNotFound;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.SQLException;

public interface DispositivoMedicaoService {

    void cadastrar(DispositivoMedicao dispositivoMedicao) throws SQLException, EnderecoNotFound;

    DispositivoMedicao buscarPorId(Long idDispositivoMedicao) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
