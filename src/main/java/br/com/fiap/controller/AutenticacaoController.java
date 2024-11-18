package br.com.fiap.controller;

import br.com.fiap.dto.LoginDto;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.exception.LoginInvalido;
import br.com.fiap.exception.LoginNotFound;
import br.com.fiap.model.Login;
import br.com.fiap.security.CookieName;
import br.com.fiap.service.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;

@Path("/auth")
public class AutenticacaoController {

    private final TokenService tokenService = TokenServiceFactory.create();
    private final LoginService loginService = LoginServiceFactory.create();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDto dto) {
        try {
            Login login = loginService.logarUsuario(dto.email(), dto.password());
            String token = tokenService.genToken(login);
            NewCookie cookie = new NewCookie(CookieName.TOKEN, token, "/", null, null, tokenService.expirationDate().getNano(), true, true);
            return Response.status(Response.Status.OK).entity(Map.of("token", token)).cookie(cookie).build();
        } catch (LoginInvalido e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "não encontrado")).build();
        } catch (SQLException | ErroAoCriarLogin e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

}
