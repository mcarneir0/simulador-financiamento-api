package io.github.mcarneir0.financiamento.simulador.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

@QuarkusTest
public class ParcelaResponseTest {

    @Test
    void testParcelaResponse() {
        ParcelaResponses.ParcelaResponse parcelaResponse = new ParcelaResponses.ParcelaResponse(
                LocalDate.now(),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(1050)
        );
        assert parcelaResponse.mes().equals(LocalDate.now());
        assert parcelaResponse.saldoInicial().equals(BigDecimal.valueOf(1000));
        assert parcelaResponse.valorJuros().equals(BigDecimal.valueOf(50));
        assert parcelaResponse.saldoFinal().equals(BigDecimal.valueOf(1050));
    }

    @Test
    void testParcelaResponseClass() {
        ParcelaResponses parcelaResponses = new ParcelaResponses();
        assert parcelaResponses.toString() != null;
    }
}
