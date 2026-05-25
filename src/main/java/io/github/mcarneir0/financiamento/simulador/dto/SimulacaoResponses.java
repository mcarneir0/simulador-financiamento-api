package io.github.mcarneir0.financiamento.simulador.dto;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.github.mcarneir0.financiamento.simulador.dto.ParcelaResponses.ParcelaResponse;

public class SimulacaoResponses {

    @Schema(name = "CriarSimulacaoResponse", description = "Resposta após criar uma nova simulação")
    public record CriarSimulacaoResponse(
            @Schema(description = "ID único da simulação gerada", examples = "1")
            Long id,
            
            @Schema(description = "Valor total a ser pago ao final do período (com juros)", examples = "1126.83")
            BigDecimal valorTotalFinal,
            
            @Schema(description = "Valor total apenas dos juros somados", examples = "126.83")
            BigDecimal valorTotalJuros,
            
            @Schema(description = "Lista contendo o detalhamento mês a mês das parcelas")
            List<ParcelaResponse> parcelas
    ) { }

    @Schema(name = "BuscarSimulacaoResponse", description = "Dados detalhados de uma simulação previamente salva")
    public record BuscarSimulacaoResponse(
            @Schema(description = "ID único da simulação gerada", examples = "1")
            Long id,
            
            @Schema(description = "Valor que foi financiado inicialmente", examples = "1000.00")
            BigDecimal valorInicial,
            
            @Schema(description = "Taxa de juros mensal que foi aplicada (em %)", examples = "1")
            double taxaJurosMensal,
            
            @Schema(description = "Prazo configurado para o financiamento", examples = "12")
            Integer prazoMeses,
            
            @Schema(description = "Valor total a ser pago ao final do período", examples = "1126.83")
            BigDecimal valorTotalFinal,
            
            @Schema(description = "Valor total apenas dos juros somados", examples = "126.83")
            BigDecimal valorTotalJuros,
            
            @Schema(description = "Lista contendo o detalhamento mês a mês das parcelas")
            List<ParcelaResponse> parcelas
    ) { }
}
