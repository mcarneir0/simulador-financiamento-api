package io.github.mcarneir0.financiamento.simulador.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@QuarkusTest
public class SimulacaoResponseTest {

    @Test
    void testCriarSimulacaoResponse() {
        SimulacaoResponses.CriarSimulacaoResponse criarSimulacaoResponse = new SimulacaoResponses.CriarSimulacaoResponse(
                1L,
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1000),
                List.of(new ParcelaResponses.ParcelaResponse(
                        LocalDate.now(),
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(1050)
                )));
        assert criarSimulacaoResponse.id().equals(1L);
        assert criarSimulacaoResponse.valorTotalFinal().equals(BigDecimal.valueOf(2000));
        assert criarSimulacaoResponse.valorTotalJuros().equals(BigDecimal.valueOf(1000));
        assert criarSimulacaoResponse.parcelas().size() == 1;
    }

    @Test
    void testBuscarSimulacaoResponse() {
        SimulacaoResponses.BuscarSimulacaoResponse buscarSimulacaoResponse = new SimulacaoResponses.BuscarSimulacaoResponse(
                1L,
                BigDecimal.valueOf(1000),
                1.2,
                12,
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1000),
                List.of(new ParcelaResponses.ParcelaResponse(
                        LocalDate.now(),
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(1050)
                )));
        assert buscarSimulacaoResponse.id().equals(1L);
        assert buscarSimulacaoResponse.valorInicial().equals(BigDecimal.valueOf(1000));
        assert buscarSimulacaoResponse.taxaJurosMensal() == 1.2;
        assert buscarSimulacaoResponse.prazoMeses() == 12;
        assert buscarSimulacaoResponse.valorTotalFinal().equals(BigDecimal.valueOf(2000));
        assert buscarSimulacaoResponse.valorTotalJuros().equals(BigDecimal.valueOf(1000));
        assert buscarSimulacaoResponse.parcelas().size() == 1;
    }

    @Test
    void testSimulacaoResponseClass() {
        SimulacaoResponses simulacaoResponses = new SimulacaoResponses();
        assert simulacaoResponses.toString() != null;
    }
}
