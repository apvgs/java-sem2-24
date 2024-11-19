package br.com.fiap.service;

import br.com.fiap.exception.*;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.SQLException;
import java.util.List;

public interface DispositivoMedicaoService {

    void cadastrar(DispositivoMedicao dispositivoMedicao) throws SQLException, EnderecoNotFound, UsuarioNotFound;

    DispositivoMedicao buscarPorId(Long idDispositivoMedicao) throws SQLException, ErroAoCriarLogin, CpfInvalido, DispositivoNotFound;

    List<DispositivoMedicao> buscarByUsuarioId(Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido;

    void alterarDispositivo(String localizacao, Long id) throws SQLException, DispositivoNotFound;

    void excluirDispositivo(Long id) throws SQLException, DispositivoNotFound;
}
