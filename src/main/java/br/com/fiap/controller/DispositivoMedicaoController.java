package br.com.fiap.controller;

import br.com.fiap.dto.DispoitivoRequestDto;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.DispositivoNotFound;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.DispositivoMedicao;
import br.com.fiap.model.Usuario;
import br.com.fiap.security.CookieName;
import br.com.fiap.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("dispositivo")
public class DispositivoMedicaoController {

    private UsuarioService usuarioService = UsuarioServiceFactory.create();
    private TokenService tokenService = TokenServiceFactory.create();
    private DispositivoMedicaoService dispositivoMedicaoService = DispositivoMedicaoServiceFactory.create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConsumo(@CookieParam(CookieName.TOKEN) String token) {
        try {
            String email = tokenService.getSubject(token);
            Usuario usuario = usuarioService.buscarUsuario(email);
            List<DispoitivoRequestDto> dispositivoMedicoes = dispositivoMedicaoService.buscarByUsuarioId(usuario.getId())
                    .stream()
                    .map(dispositivo -> new DispoitivoRequestDto(dispositivo.getId(), dispositivo.getLocalizacao(), dispositivo.getUsuario().getNome()))
                    .toList();
            return Response.ok(dispositivoMedicoes).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "login não encontrado")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsumoById(@PathParam("id") Long id) {
        try {
            DispositivoMedicao dispositivoMedicoes = dispositivoMedicaoService.buscarPorId(id);
            return Response.ok(new DispoitivoRequestDto(dispositivoMedicoes.getId(), dispositivoMedicoes.getLocalizacao(), dispositivoMedicoes.getUsuario().getNome())).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (DispositivoNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDispositivo(@PathParam("id") Long id, DispoitivoRequestDto request) {
        try {
            dispositivoMedicaoService.alterarDispositivo(request.localizacao(), id);
            DispositivoMedicao dispositivoMedicao = dispositivoMedicaoService.buscarPorId(id);
            return Response.ok(new DispoitivoRequestDto(dispositivoMedicao.getId(), dispositivoMedicao.getLocalizacao(), dispositivoMedicao.getUsuario().getNome())).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (DispositivoNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirDispositivo(@PathParam("id") Long id) {
        try {
            dispositivoMedicaoService.excluirDispositivo(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (DispositivoNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        }
    }

}
