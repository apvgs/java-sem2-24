package br.com.fiap.controller;

import br.com.fiap.dto.EnderecoDto;
import br.com.fiap.exception.CepInvalido;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.Endereco;
import br.com.fiap.model.Usuario;
import br.com.fiap.security.CookieName;
import br.com.fiap.service.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;

@Path("endereco")
public class EnderecoController {

    private EnderecoService enderecoService = EnderecoServiceFactory.create();
    private UsuarioService usuarioService = UsuarioServiceFactory.create();
    private TokenService tokenService = TokenServiceFactory.create();

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEndereco(@CookieParam(CookieName.TOKEN) String token, EnderecoDto dto) {
        try {
            String email = tokenService.getSubject(token);
            Usuario usuario = usuarioService.buscarUsuario(email);
            Endereco endereco = new Endereco(dto.cep(), dto.numero(), usuario, dto.apelido());
            enderecoService.cadastrarEndereco(endereco);
            return Response.status(Response.Status.CREATED).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CepInvalido | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "usuario não encontrado")).build();
        }
    }
}
