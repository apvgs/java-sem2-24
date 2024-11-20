package br.com.fiap.controller;

import br.com.fiap.dto.ConsumoMesDto;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.EmailJaExistente;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.ConsumoDiario;
import br.com.fiap.model.Usuario;
import br.com.fiap.security.CookieName;
import br.com.fiap.service.*;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("consumo_diario")
public class ConsumoController {

    private UsuarioService usuarioService = UsuarioServiceFactory.create();
    private TokenService tokenService = TokenServiceFactory.create();
    private ConsumoDiarioService consumoDiarioService = ConsumoDiarioServiceFactory.create();


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConsumoMensal(@CookieParam(CookieName.TOKEN) String token) {
        try {
            String email = tokenService.getSubject(token);
            Usuario usuario = usuarioService.buscarUsuario(email);
            ConsumoMesDto consumoMesDto = consumoDiarioService.getConsumoMes(usuario.getId());
            return Response.ok(consumoMesDto).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "login não encontrado")).build();
        }
    }
}
