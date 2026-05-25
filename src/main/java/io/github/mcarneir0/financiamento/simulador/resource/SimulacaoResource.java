package io.github.mcarneir0.financiamento.simulador.resource;

import io.github.mcarneir0.financiamento.simulador.dto.ParcelaResponses;
import io.github.mcarneir0.financiamento.simulador.dto.SimulacaoRequests.CriarSimulacaoRequest;
import io.github.mcarneir0.financiamento.simulador.dto.SimulacaoResponses;
import io.github.mcarneir0.financiamento.simulador.dto.SimulacaoResponses.CriarSimulacaoResponse;
import io.github.mcarneir0.financiamento.simulador.entity.Simulacao;
import io.github.mcarneir0.financiamento.simulador.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.RoundingMode;
import java.net.URI;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Simulações", description = "Endpoints para gerenciamento e cálculo de simulações de financiamento")
public class SimulacaoResource {

    @Inject
    private SimulacaoService simulacaoService;

    @POST
    @Operation(summary = "Criar nova simulação", description = "Calcula e persiste uma simulação de financiamento baseada em juros compostos.")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Simulação criada com sucesso", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = CriarSimulacaoResponse.class))),
        @APIResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public Response criarSimulacao(@Valid CriarSimulacaoRequest request) {

        Simulacao simulacao = simulacaoService.calcularSimulacao(
                request.valorInicial(),
                request.taxaJurosMensal(),
                request.prazoMeses()
        );

        return Response
                .created(URI.create("/simulacoes/" + simulacao.id))
                .entity(new CriarSimulacaoResponse(
                        simulacao.id,
                        simulacao.getValorTotalFinal().setScale(2, RoundingMode.HALF_UP),
                        simulacao.getValorTotalJuros().setScale(2, RoundingMode.HALF_UP),
                        simulacao.getParcelas().stream().map(parcela -> new ParcelaResponses.ParcelaResponse(
                                parcela.getMes(),
                                parcela.getSaldoInicial().setScale(2, RoundingMode.HALF_UP),
                                parcela.getValorJuros().setScale(2, RoundingMode.HALF_UP),
                                parcela.getSaldoFinal().setScale(2, RoundingMode.HALF_UP)
                        )).toList()
                ))
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar simulação pelo ID", description = "Recupera os detalhes de uma simulação salva previamente.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Simulação encontrada",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimulacaoResponses.BuscarSimulacaoResponse.class))),
        @APIResponse(responseCode = "404", description = "Simulação não encontrada")
    })
    public Response buscarSimulacaoPeloId(
            @Parameter(description = "ID da simulação", required = true) 
            @PathParam("id") @Min(1) Long id) {
        return simulacaoService.buscarSimulacaoPeloId(id)
                .map(simulacao -> Response.ok(new SimulacaoResponses.BuscarSimulacaoResponse(
                        simulacao.id,
                        simulacao.getValorInicial().setScale(2, RoundingMode.HALF_UP),
                        simulacao.getTaxaJurosMensal() * 100,
                        simulacao.getPrazoMeses(),
                        simulacao.getValorTotalFinal().setScale(2, RoundingMode.HALF_UP),
                        simulacao.getValorTotalJuros().setScale(2, RoundingMode.HALF_UP),
                        simulacao.getParcelas().stream().map(parcela -> new ParcelaResponses.ParcelaResponse(
                                parcela.getMes(),
                                parcela.getSaldoInicial().setScale(2, RoundingMode.HALF_UP),
                                parcela.getValorJuros().setScale(2, RoundingMode.HALF_UP),
                                parcela.getSaldoFinal().setScale(2, RoundingMode.HALF_UP)
                        )).toList()
                )).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
