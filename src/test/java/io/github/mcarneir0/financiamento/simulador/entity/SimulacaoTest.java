package io.github.mcarneir0.financiamento.simulador.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@QuarkusTest
public class SimulacaoTest {

    @Test
    void testSimulacao() {
        Simulacao simulacao = new Simulacao();
        simulacao.setValorInicial(BigDecimal.valueOf(1000));
        simulacao.setValorTotalFinal(BigDecimal.valueOf(1100));
        simulacao.setValorTotalJuros(BigDecimal.valueOf(100));
        simulacao.setTaxaJurosMensal(0.01);
        simulacao.setPrazoMeses(12);
        simulacao.setParcelas(List.of(new Parcela(
                LocalDate.now(),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(150)
        )));
        assert simulacao.getValorInicial() != null;
        assert simulacao.getValorTotalFinal() != null;
        assert simulacao.getValorTotalJuros() != null;
        assert simulacao.getTaxaJurosMensal() > 0;
        assert simulacao.getPrazoMeses() > 0;
        assert simulacao.getParcelas() != null;
    }

    @Test
    void testSimulacaoConstrutor() {
        Simulacao simulacao = new Simulacao(
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(1100),
                BigDecimal.valueOf(100),
                0.01,
                12,
                List.of(new Parcela(
                        LocalDate.now(),
                        BigDecimal.valueOf(100),
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(150)
                ))
        );
        assert simulacao.getValorInicial() != null;
        assert simulacao.getValorTotalFinal() != null;
        assert simulacao.getValorTotalJuros() != null;
        assert simulacao.getTaxaJurosMensal() > 0;
        assert simulacao.getPrazoMeses() > 0;
        assert simulacao.getParcelas() != null;
    }
}
