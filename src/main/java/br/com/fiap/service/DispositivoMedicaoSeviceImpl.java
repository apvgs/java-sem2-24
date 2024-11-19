package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.DispositivoMedicaoDao;
import br.com.fiap.dao.DispositivoMedicaoDaoFactory;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.DispositivoNotFound;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.UsuarioNotFound;
import br.com.fiap.model.DispositivoMedicao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class DispositivoMedicaoSeviceImpl implements DispositivoMedicaoService{

    private DispositivoMedicaoDao dispositivoMedicaoDao = DispositivoMedicaoDaoFactory.getDispositivoMedicaoDao();

    @Override
    public void cadastrar(DispositivoMedicao dispositivoMedicao) throws SQLException, UsuarioNotFound {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            if (dispositivoMedicao.getUsuario() == null){
                throw new UsuarioNotFound();
            }
            dispositivoMedicaoDao.inserir(connection, dispositivoMedicao);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            connection.close();
        }
    }


    @Override
    public DispositivoMedicao buscarPorId(Long idDispositivoMedicao) throws SQLException, ErroAoCriarLogin, CpfInvalido, DispositivoNotFound {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            DispositivoMedicao buscar = dispositivoMedicaoDao.buscar(connection, idDispositivoMedicao);
            if (buscar == null){
                throw new DispositivoNotFound("Dispositivo não encontrado");
            }
            return buscar;
        }
    }

    @Override
    public List<DispositivoMedicao> buscarByUsuarioId(Long userId) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return dispositivoMedicaoDao.buscarByUsuarioId(connection, userId);
        }
    }

    @Override
    public void alterarDispositivo(String localizacao, Long id) throws SQLException, DispositivoNotFound {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            dispositivoMedicaoDao.alterarDispositivo(connection, localizacao, id);
        }
    }

    @Override
    public void excluirDispositivo(Long id) throws SQLException, DispositivoNotFound {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            dispositivoMedicaoDao.excluirDispositivo(connection, id);
        }
    }

}
