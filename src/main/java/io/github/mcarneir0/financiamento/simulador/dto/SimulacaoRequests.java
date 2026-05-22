package io.github.mcarneir0.financiamento.simulador.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SimulacaoRequests {

    public record CriarSimulacaoRequest(
            @NotNull @Min(1) BigDecimal valorInicial,
            @NotNull double taxaJurosMensal,
            @NotNull @Min(1) Integer prazoMeses
    ) { }
}
