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

import java.math.RoundingMode;
import java.net.URI;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    private SimulacaoService simulacaoService;

    @POST
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
    public Response buscarSimulacaoPeloId(@PathParam("id") @Min(1) Long id) {
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
