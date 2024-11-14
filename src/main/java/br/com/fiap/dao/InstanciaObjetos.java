package br.com.fiap.dao;

import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.Endereco;
import br.com.fiap.model.Login;
import br.com.fiap.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

final class InstanciaObjetos {

    public static Login instanciaLogin(ResultSet rs) throws SQLException, ErroAoCriarLogin {
        Login login = new Login();
        login.setSenha(rs.getString("senha"));
        login.setEmail(rs.getString("email"));
        login.setId(rs.getLong("id_login"));
        return login;
    }

    public static Usuario instanciaUsuario(ResultSet rs) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));
        usuario.setNome(rs.getString("nome"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setLogin(instanciaLogin(rs));
        return usuario;
    }

    public static Endereco instanciaEndereco(ResultSet rs) throws SQLException, ErroAoCriarLogin, CpfInvalido {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getLong("id_endereco"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCep(rs.getString("cep"));
        endereco.setUsuario(instanciaUsuario(rs));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getInt("numero"));
        return endereco;
    }

    public static ConsumoDiario instanciaConsumoDiario(ResultSet rs) throws SQLException, CpfInvalido, ErroAoCriarLogin {
        ConsumoDiario consumoDiario = new ConsumoDiario();
        consumoDiario.setId(rs.getLong("id_consumo"));
        consumoDiario.setDate(rs.getDate("data").toLocalDate());
        consumoDiario.setConsumoDiario(rs.getDouble("consumo_diario"));
        consumoDiario.setEndereco(instanciaEndereco(rs));
        return consumoDiario;
    }
}
