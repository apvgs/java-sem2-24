package br.com.fiap.controller;

import br.com.fiap.dto.NotificacaoDto;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginNotFound;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Path("notificacao")
public class NotificacaoController {

    private NotificacaoService notificacaoService = NotificacaoServiceFactory.create();
    private UsuarioService usuarioService = UsuarioServiceFactory.create();
    private TokenService tokenService = TokenServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotificacao(@CookieParam(CookieName.TOKEN) String token) {
        try {
            String email = tokenService.getSubject(token);
            Usuario usuario = usuarioService.buscarUsuario(email);
            List<NotificacaoDto> notificacao = notificacaoService.listar(usuario.getId())
                    .stream()
                    .map(n -> new NotificacaoDto(n.getId(), n.getMensagem(), n.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))))
                    .toList();
            return Response.ok(notificacao).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error",e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido | LoginNotFound e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", e.getMessage())).build();
        }
    }
}
