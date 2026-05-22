package io.github.mcarneir0.financiamento.simulador.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelaResponses {

    public record ParcelaResponse(
            LocalDate mes,
            BigDecimal saldoInicial,
            BigDecimal valorJuros,
            BigDecimal saldoFinal
    ) { }
}
