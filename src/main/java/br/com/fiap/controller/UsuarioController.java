package br.com.fiap.controller;


import br.com.fiap.dto.CadastroUsuarioDto;
import br.com.fiap.dto.DashBoardDto;
import br.com.fiap.dto.DispoitivoRequestDto;
import br.com.fiap.dto.DispositivoDto;
import br.com.fiap.exception.*;
import br.com.fiap.model.DispositivoMedicao;
import br.com.fiap.model.Endereco;
import br.com.fiap.model.Login;
import br.com.fiap.model.Usuario;
import br.com.fiap.security.CookieName;
import br.com.fiap.service.*;

import javax.ws.rs.*;
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
    private TokenService tokenService = TokenServiceFactory.create();
    private EnderecoService enderecoService = EnderecoServiceFactory.create();
    private DispositivoMedicaoService dispositivoMedicaoService = DispositivoMedicaoServiceFactory.create();
    @Context
    private UriInfo uriInfo;

    @POST
    @Path("auth/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(CadastroUsuarioDto dto) {
        try {
            Login login = new Login(dto.email(), dto.password());
            Usuario usuario = new Usuario(dto.cpf(), dto.name(), login);
            usuarioService.cadastrarUsuario(usuario);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(usuario.getId())).build();
            return Response.created(uri).entity(dto).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (EmailJaExistente e) {
            return Response.status(Response.Status.CONFLICT).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/dashboard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dashBoard(@CookieParam(CookieName.TOKEN) String token) {
        try {
            String email = tokenService.getSubject(token);
            DashBoardDto dashboard = usuarioService.dashBoard(email);
            return Response.ok().entity(dashboard).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "login não encontrado")).build();
        }
    }

    @POST
    @Path("add_dispositivo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDispositivo(@CookieParam(CookieName.TOKEN) String token, DispositivoDto dto){
        try {
            String email = tokenService.getSubject(token);
            Usuario usuario = usuarioService.buscarUsuario(email);
            DispositivoMedicao dispositivoMedicao = new DispositivoMedicao(dto.codigo(), dto.localizacao(), usuario);
            dispositivoMedicaoService.cadastrar(dispositivoMedicao);
            return Response.status(Response.Status.CREATED).entity(
                    new DispoitivoRequestDto(dispositivoMedicao.getId(), dispositivoMedicao.getLocalizacao(), dispositivoMedicao.getUsuario().getNome(), dispositivoMedicao.getCodigo()))
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido | EnderecoNotFound | UsuarioNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
           return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", e.getMessage())).build();
        }
    }

}
