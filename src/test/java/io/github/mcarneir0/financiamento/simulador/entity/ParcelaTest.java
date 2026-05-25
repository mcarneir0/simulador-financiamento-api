package io.github.mcarneir0.financiamento.simulador.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

@QuarkusTest
public class ParcelaTest {

    @Test
    void testParcela() {
        Parcela parcela = new Parcela();
        parcela.setMes(LocalDate.now());
        parcela.setSaldoFinal(BigDecimal.valueOf(150));
        parcela.setSaldoInicial(BigDecimal.valueOf(100));
        parcela.setValorJuros(BigDecimal.valueOf(50));

        assert parcela.getMes().equals(LocalDate.now());
        assert parcela.getSaldoInicial().equals(BigDecimal.valueOf(100));
        assert parcela.getSaldoFinal().equals(BigDecimal.valueOf(150));
        assert parcela.getValorJuros().equals(BigDecimal.valueOf(50));
    }

    @Test
    void testParcelaConstrutor() {
        Parcela parcela = new Parcela(
                LocalDate.now(),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(150)
        );

        assert parcela.getMes().equals(LocalDate.now());
        assert parcela.getSaldoInicial().equals(BigDecimal.valueOf(100));
        assert parcela.getValorJuros().equals(BigDecimal.valueOf(50));
        assert parcela.getSaldoFinal().equals(BigDecimal.valueOf(150));
    }
}
