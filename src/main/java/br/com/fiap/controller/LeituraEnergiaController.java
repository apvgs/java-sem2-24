package br.com.fiap.controller;

import br.com.fiap.dto.LeituraEnergiaDto;
import br.com.fiap.dto.LeituraEnergiaResponse;
import br.com.fiap.exception.ConsumoNotFound;
import br.com.fiap.exception.CpfInvalido;
import br.com.fiap.exception.DispositivoNotFound;
import br.com.fiap.exception.ErroAoCriarLogin;
import br.com.fiap.model.DispositivoMedicao;
import br.com.fiap.model.LeituraEnergia;
import br.com.fiap.service.DispositivoMedicaoService;
import br.com.fiap.service.DispositivoMedicaoServiceFactory;
import br.com.fiap.service.LeituraEnergiaService;
import br.com.fiap.service.LeituraEnergiaServiceFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Path("leituraEnergia")
public class LeituraEnergiaController {

    private LeituraEnergiaService leituraEnergiaService = LeituraEnergiaServiceFactory.create();
    private DispositivoMedicaoService dispositivoMedicaoService = DispositivoMedicaoServiceFactory.create();

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

}
