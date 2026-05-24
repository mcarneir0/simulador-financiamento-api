package io.github.mcarneir0.financiamento.simulador.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@QuarkusTest
public class SimulacaoServiceTest {

    @Test
    @Transactional
    void testCriarSimulacaoEbuscarPeloId() {
        SimulacaoService simulacaoService = new SimulacaoService();
            simulacaoService.calcularSimulacao(
                    BigDecimal.valueOf(1000),
                    1.0,
                    12
            );
            assert simulacaoService.buscarSimulacaoPeloId(1L).isPresent();
    }

    @Test
    void testCriarSimulacaoClass() {
        SimulacaoService simulacaoService = new SimulacaoService();
        assert simulacaoService.toString() != null;
    }
}
