package io.github.mcarneir0.financiamento.simulador.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public class SimulacaoRequests {

    @Schema(name = "CriarSimulacaoRequest", description = "Dados necessários para iniciar uma simulação de financiamento")
    public record CriarSimulacaoRequest(
            @Schema(description = "Valor inicial do financiamento", required = true, examples = "1000.00")
            @NotNull @Min(1) BigDecimal valorInicial,
            
            @Schema(description = "Taxa de juros mensal (em porcentagem)", required = true, examples = "1")
            @NotNull double taxaJurosMensal,
            
            @Schema(description = "Prazo do financiamento em meses", required = true, examples = "12")
            @NotNull @Min(1) Integer prazoMeses
    ) { }
}
