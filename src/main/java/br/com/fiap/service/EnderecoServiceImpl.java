package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.EnderecoDao;
import br.com.fiap.dao.EnderecoDaoFactory;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Endereco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class EnderecoServiceImpl implements EnderecoService {

    EnderecoDao enderecoDao = EnderecoDaoFactory.getEnderecoDao();

    @Override
    public void cadastrarEndereco(Endereco endereco) throws SQLException {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            enderecoDao.cadastrar(connection, endereco);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public List<Endereco> listarEndereco(Long idUsuario) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return enderecoDao.getEnderecosByUsuaroId(connection, idUsuario);
        }
    }

    @Override
    public Endereco buscarEndereco(Long id) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        try(Connection connection = DatabaseConnectionFactory.getConnection()) {
            return enderecoDao.getEndereco(connection, id);
        }
    }
}
