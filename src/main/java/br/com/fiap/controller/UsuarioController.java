package br.com.fiap.controller;


import br.com.fiap.dto.CadastroUsuarioDto;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.EmailJaExistente;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.Login;
import br.com.fiap.model.Usuario;
import br.com.fiap.service.UsuarioService;
import br.com.fiap.service.UsuarioServiceFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.sql.SQLException;
import java.util.Map;

@Path("user")
public class UsuarioController {

    private UsuarioService usuarioService = UsuarioServiceFactory.create();
    @Context
    private UriInfo uriInfo;

    @POST
    @Path("auth/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(CadastroUsuarioDto dto) {
        try {
            Login login = new Login(dto.email(), dto.senha());
            Usuario usuario = new Usuario(dto.cpf(), dto.nome(), login);
            usuarioService.cadastrarUsuario(usuario);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(usuario.getId())).build();
            return Response.created(uri).entity(dto).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("message", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (EmailJaExistente e) {
            return Response.status(Response.Status.CONFLICT).entity(Map.of("error", e.getMessage())).build();
        }
    }


}
