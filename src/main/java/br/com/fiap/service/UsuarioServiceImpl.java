package br.com.fiap.service;

import br.com.fiap.config.DatabaseConnectionFactory;
import br.com.fiap.dao.UsuarioDao;
import br.com.fiap.dao.UsuarioDaoFactory;
import br.com.fiap.dto.DashBoardDto;
import br.com.fiap.dto.LeituraEnergiaResponse;
import br.com.fiap.exception.*;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.Login;
import br.com.fiap.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

final class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDao usuarioDao = UsuarioDaoFactory.getUsuarioDao();
    private LoginService loginService = LoginServiceFactory.create();
    private ConsumoDiarioService consumoDiarioService = ConsumoDiarioServiceFactory.create();
    private LeituraEnergiaService leituraEnergiaService = LeituraEnergiaServiceFactory.create();
    private DispositivoMedicaoService dispositivoMedicaoService = DispositivoMedicaoServiceFactory.create();


    @Override
    public void cadastrarUsuario(Usuario usuario) throws SQLException, ErroAoCriarLogin, EmailJaExistente {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            loginService.create(usuario.getLogin(), connection);
            usuarioDao.inserir(connection, usuario);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public void alterarUsuario(Usuario usuario) throws SQLException, UsuarioNotFound {
        Connection connection = DatabaseConnectionFactory.getConnection();
        try {
            usuarioDao.alterar(connection, usuario);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }
    }

    @Override
    public Usuario buscarUsuario(String email) throws SQLException, ErroAoCriarLogin, CpfInvalido, LoginNotFound {
        Connection connection = DatabaseConnectionFactory.getConnection();
        Login login = loginService.findByEmail(email);
        return usuarioDao.buscarPorLoginId(connection, login.getId());
    }

    @Override
    public DashBoardDto dashBoard(String email) throws SQLException, ErroAoCriarLogin, LoginNotFound, CpfInvalido {
        Login login = loginService.findByEmail(email);
        Connection connection = DatabaseConnectionFactory.getConnection();
        Usuario usuario = usuarioDao.buscarPorLoginId(connection, login.getId());
        ConsumoDiario consumoDiario = consumoDiarioService.buscarPorId(usuario.getId(), LocalDate.now());
        List<LeituraEnergiaResponse> leituraEnergias = leituraEnergiaService.listar(usuario.getId(), LocalDate.now())
                .stream()
                .map(leituraEnergia -> new LeituraEnergiaResponse(leituraEnergia.getId(),
                        leituraEnergia.getDataMedicao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss")),
                        leituraEnergia.getConsumo(),
                        leituraEnergia.getDispositivoMedicao().getCodigo()))
                .toList();
        List<ConsumoDiario> consumoDiarios = consumoDiarioService.buscarConsumoDiarioByUsuarioIdEMes(usuario.getId());
        double consumoMensal = 0;
        for (ConsumoDiario consumo : consumoDiarios) {
            consumoMensal += consumo.getConsumoDiario();
        }
        double mediaDiaria;
        if(consumoDiarios.isEmpty()){
            mediaDiaria = 0;
        } else {
            mediaDiaria = consumoMensal / consumoDiarios.size();
        }
        boolean temDispositivo = !dispositivoMedicaoService.buscarByUsuarioId(usuario.getId()).isEmpty();

        return new DashBoardDto(usuario.getNome(),
                usuario.getId(),
                usuario.getCpf(),
                login.getEmail(),
                login.getSenha(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ,
                consumoDiario == null ? 0 : consumoDiario.getConsumoDiario(),
                consumoMensal,
                mediaDiaria,
                leituraEnergias,
                temDispositivo
                );
    }
}
