package io.github.mcarneir0.financiamento.simulador.service;

import io.github.mcarneir0.financiamento.simulador.entity.Simulacao;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("SimulacaoService - Testes de Integração/Serviço")
public class SimulacaoServiceTest {

    @Inject
    SimulacaoService simulacaoService;

    @Test
    @Transactional
    @DisplayName("Deve calcular, persistir e buscar a simulação corretamente")
    void testCriarSimulacaoEbuscarPeloId() {
        BigDecimal valorInicial = BigDecimal.valueOf(1000);
        double taxaJurosMensal = 1.0;
        int prazoMeses = 12;

        Simulacao simulacao = simulacaoService.calcularSimulacao(valorInicial, taxaJurosMensal, prazoMeses);

        assertNotNull(simulacao.id, "O ID da simulação não deveria ser nulo após persistir");
        assertEquals(12, simulacao.getParcelas().size(), "Deveria ter gerado 12 parcelas");
        
        BigDecimal expectedValorFinal = BigDecimal.valueOf(1000 * Math.pow(1.01, 12));
        assertTrue(simulacao.getValorTotalFinal().compareTo(expectedValorFinal) == 0 || 
                   simulacao.getValorTotalFinal().subtract(expectedValorFinal).abs().compareTo(BigDecimal.valueOf(0.01)) < 0,
                   "Valor final calculado incorretamente");

        Optional<Simulacao> simulacaoBuscada = simulacaoService.buscarSimulacaoPeloId(simulacao.id);
        assertTrue(simulacaoBuscada.isPresent(), "Deveria encontrar a simulação no banco");
        assertEquals(simulacao.id, simulacaoBuscada.get().id);
    }

    @Test
    @Transactional
    @DisplayName("Deve calcular simulação de apenas 1 mês (borda inferior de prazo válido)")
    void testCriarSimulacaoUmMes() {
        BigDecimal valorInicial = BigDecimal.valueOf(500);
        
        Simulacao simulacao = simulacaoService.calcularSimulacao(valorInicial, 2.0, 1);
        
        assertEquals(1, simulacao.getParcelas().size());
        assertEquals(0, BigDecimal.valueOf(510.0).compareTo(simulacao.getValorTotalFinal()), 
            "500 + 2% deveria ser exatamente 510");
    }

    @Test
    @DisplayName("Deve retornar Optional.empty() ao buscar ID inexistente")
    void testBuscarSimulacaoInexistente() {
        Optional<Simulacao> result = simulacaoService.buscarSimulacaoPeloId(999999L);
        assertTrue(result.isEmpty(), "Deveria retornar empty para ID que não existe");
    }

    @Test
    @DisplayName("Deve lançar exceção se prazoMeses for 0 ou negativo (borda inferior inválida)")
    void testPrazoMesesInvalido() {
        assertThrows(NoSuchElementException.class, () -> {
            simulacaoService.calcularSimulacao(BigDecimal.valueOf(1000), 1.0, 0);
        }, "Deveria falhar ao tentar pegar a última parcela de uma lista vazia (prazo = 0)");
        
        assertThrows(NoSuchElementException.class, () -> {
            simulacaoService.calcularSimulacao(BigDecimal.valueOf(1000), 1.0, -5);
        }, "Deveria falhar ao tentar pegar a última parcela de uma lista vazia (prazo negativo)");
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se valorInicial for nulo")
    void testValorInicialNulo() {
        assertThrows(NullPointerException.class, () -> {
            simulacaoService.calcularSimulacao(null, 1.0, 12);
        }, "Deveria falhar se o valorInicial for nulo devido as operações matemáticas");
    }

    @Test
    @DisplayName("Teste da instância da classe SimulacaoService")
    void testSimulacaoServiceClass() {
        assertNotNull(simulacaoService.toString());
    }
}
