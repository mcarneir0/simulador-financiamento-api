package io.github.mcarneir0.financiamento.simulador.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelaResponses {

    @Schema(name = "ParcelaResponse", description = "Detalhes de uma parcela da simulação")
    public record ParcelaResponse(
            @Schema(description = "Mês de vencimento da parcela", examples = "2026-06-01")
            LocalDate mes,
            
            @Schema(description = "Saldo devedor inicial no início do mês", examples = "100.00")
            BigDecimal saldoInicial,
            
            @Schema(description = "Valor dos juros aplicados na parcela", examples = "5.00")
            BigDecimal valorJuros,
            
            @Schema(description = "Saldo devedor final após aplicação dos juros", examples = "105.00")
            BigDecimal saldoFinal
    ) { }
}
