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
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(2000),
                List.of(new ParcelaResponses.ParcelaResponse(
                        LocalDate.now(),
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(1050)
                )));
        assert criarSimulacaoResponse.id() != null;
        assert criarSimulacaoResponse.valorTotalFinal() != null;
        assert criarSimulacaoResponse.valorTotalJuros() != null;
        assert criarSimulacaoResponse.parcelas() != null;
    }

    @Test
    void testBuscarSimulacaoResponse() {
        SimulacaoResponses.BuscarSimulacaoResponse buscarSimulacaoResponse = new SimulacaoResponses.BuscarSimulacaoResponse(
                1L,
                BigDecimal.valueOf(1000),
                1.2,
                12,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(2000),
                List.of(new ParcelaResponses.ParcelaResponse(
                        LocalDate.now(),
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(1050)
                )));
        assert buscarSimulacaoResponse.id() != null;
        assert buscarSimulacaoResponse.valorInicial() != null;
        assert buscarSimulacaoResponse.taxaJurosMensal() > 0;
        assert buscarSimulacaoResponse.prazoMeses() != null;
        assert buscarSimulacaoResponse.valorTotalFinal() != null;
        assert buscarSimulacaoResponse.valorTotalJuros() != null;
        assert buscarSimulacaoResponse.parcelas() != null;
    }

    @Test
    void testSimulacaoResponseClass() {
        SimulacaoResponses simulacaoResponses = new SimulacaoResponses();
        assert simulacaoResponses.toString() != null;
    }
}
