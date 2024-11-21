package br.com.fiap.controller;

import br.com.fiap.dto.LeituraEnergiaDto;
import br.com.fiap.dto.LeituraEnergiaResponse;
import br.com.fiap.exception.*;
import br.com.fiap.model.DispositivoMedicao;
import br.com.fiap.model.LeituraEnergia;
import br.com.fiap.model.Usuario;
import br.com.fiap.security.CookieName;
import br.com.fiap.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Path("leitura_energia")
public class LeituraEnergiaController {

    private LeituraEnergiaService leituraEnergiaService = LeituraEnergiaServiceFactory.create();
    private DispositivoMedicaoService dispositivoMedicaoService = DispositivoMedicaoServiceFactory.create();
    private UsuarioService usuarioService = UsuarioServiceFactory.create();
    private TokenService tokenService = TokenServiceFactory.create();

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirLeituraEnergia(LeituraEnergiaDto dto) {
        try {
            DispositivoMedicao dispositivoMedicao = dispositivoMedicaoService.buscarPorId(dto.dispositivoId());
            LeituraEnergia leituraEnergia = new LeituraEnergia();
            leituraEnergia.setConsumo(dto.consumo());
            leituraEnergia.setDataMedicao(LocalDateTime.now());
            leituraEnergia.setDispositivoMedicao(dispositivoMedicao);
            leituraEnergiaService.inserir(leituraEnergia);
            LeituraEnergiaResponse response = new LeituraEnergiaResponse(leituraEnergia.getId(),
                    leituraEnergia.getDataMedicao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    leituraEnergia.getConsumo(),
                    leituraEnergia.getDispositivoMedicao().getCodigo());
            return Response.ok(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido | DispositivoNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (ConsumoNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "consumo não encontrado")).build();
        }
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarLeituraEnergia(@QueryParam("data") String data, @CookieParam(CookieName.TOKEN) String token) {
        try {
            LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String email = tokenService.getSubject(token);
            Usuario usuario = usuarioService.buscarUsuario(email);
            List<LeituraEnergiaResponse> listar = leituraEnergiaService.listar(usuario.getId(), localDate)
                    .stream()
                    .map(x -> new LeituraEnergiaResponse(x.getId(),
                            x.getDataMedicao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss")),
                            x.getConsumo(),
                            x.getDispositivoMedicao().getCodigo()))
                    .toList();
            return Response.ok(listar).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        } catch (ErroAoCriarLogin | CpfInvalido e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (LoginNotFound e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Map.of("error", e.getMessage())).build();
        }
    }

}
