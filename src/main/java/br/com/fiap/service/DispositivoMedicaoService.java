package br.com.fiap.service;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.EnderecoNotFound;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.UsuarioNotFound;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.SQLException;
import java.util.List;

public interface DispositivoMedicaoService {

    void cadastrar(DispositivoMedicao dispositivoMedicao) throws SQLException, EnderecoNotFound, UsuarioNotFound;

    DispositivoMedicao buscarPorId(Long idDispositivoMedicao) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    List<DispositivoMedicao> buscarByUsuarioId(Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido;
}
