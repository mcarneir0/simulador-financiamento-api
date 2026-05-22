package io.github.mcarneir0.financiamento.simulador.dto;

import java.math.BigDecimal;
import java.util.List;

import io.github.mcarneir0.financiamento.simulador.dto.ParcelaResponses.ParcelaResponse;

public class SimulacaoResponses {

    public record CriarSimulacaoResponse(
            Long id,
            BigDecimal valorTotalFinal,
            BigDecimal valorTotalJuros,
            List<ParcelaResponse> parcelas
    ) { }

    public record BuscarSimulacaoResponse(
            Long id,
            BigDecimal valorInicial,
            double taxaJurosMensal,
            Integer prazoMeses,
            BigDecimal valorTotalFinal,
            BigDecimal valorTotalJuros,
            List<ParcelaResponse> parcelas
    ) { }
}
