package io.github.mcarneir0.financiamento.simulador.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@QuarkusTest
public class SimulacaoRequestTest {

    @Test
    void testCriarSimulacaoRequest() {
        SimulacaoRequests.CriarSimulacaoRequest criarSimulacaoRequest = new SimulacaoRequests.CriarSimulacaoRequest(
                BigDecimal.valueOf(1000),
                1.2,
                12
        );
        assert criarSimulacaoRequest.valorInicial() != null;
        assert criarSimulacaoRequest.taxaJurosMensal() > 0;
        assert criarSimulacaoRequest.prazoMeses() != null;
    }

    @Test
    void testCriarSimulacaoClass() {
        SimulacaoRequests simulacaoRequests = new SimulacaoRequests();
        assert simulacaoRequests.toString() != null;
    }
}
