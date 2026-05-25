package io.github.mcarneir0.financiamento.simulador.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("SimulacaoResponse - Testes de unidade")
public class SimulacaoResponseTest {

    private ParcelaResponses.ParcelaResponse criarParcelaMock() {
        return new ParcelaResponses.ParcelaResponse(
                LocalDate.of(2026, 6, 1),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(1050)
        );
    }

    @Nested
    @DisplayName("CriarSimulacaoResponse")
    class CriarSimulacaoResponseTest {

        @Test
        @DisplayName("Deve criar CriarSimulacaoResponse com todos os campos preenchidos corretamente")
        void deveCriarComTodosOsCampos() {
            Long id = 1L;
            BigDecimal valorTotalFinal = BigDecimal.valueOf(2000);
            BigDecimal valorTotalJuros = BigDecimal.valueOf(1000);
            List<ParcelaResponses.ParcelaResponse> parcelas = List.of(criarParcelaMock());

            SimulacaoResponses.CriarSimulacaoResponse response = new SimulacaoResponses.CriarSimulacaoResponse(
                    id, valorTotalFinal, valorTotalJuros, parcelas
            );

            assertAll("response criada via construtor",
                    () -> assertEquals(id, response.id()),
                    () -> assertEquals(valorTotalFinal, response.valorTotalFinal()),
                    () -> assertEquals(valorTotalJuros, response.valorTotalJuros()),
                    () -> assertEquals(1, response.parcelas().size())
            );
        }

        @Test
        @DisplayName("Deve aceitar valores nulos (erros/borda)")
        void deveAceitarValoresNulos() {
            assertDoesNotThrow(() -> {
                SimulacaoResponses.CriarSimulacaoResponse response = new SimulacaoResponses.CriarSimulacaoResponse(
                        null, null, null, null
                );
                assertNull(response.id());
                assertNull(response.valorTotalFinal());
                assertNull(response.valorTotalJuros());
                assertNull(response.parcelas());
            }, "Record deve aceitar campos nulos, a menos que exista validação no construtor compacto");
        }

        @Test
        @DisplayName("Deve aceitar lista de parcelas vazia (borda)")
        void deveAceitarListaDeParcelasVazia() {
            SimulacaoResponses.CriarSimulacaoResponse response = new SimulacaoResponses.CriarSimulacaoResponse(
                    1L, BigDecimal.ZERO, BigDecimal.ZERO, Collections.emptyList()
            );

            assertTrue(response.parcelas().isEmpty(), "Deveria aceitar e retornar uma lista vazia");
        }

        @Test
        @DisplayName("Duas instâncias com mesmos valores devem ser iguais e ter o mesmo hashCode")
        void deveSerIgualSeValoresIguais() {
            List<ParcelaResponses.ParcelaResponse> parcelas = List.of(criarParcelaMock());
            SimulacaoResponses.CriarSimulacaoResponse r1 = new SimulacaoResponses.CriarSimulacaoResponse(
                    1L, BigDecimal.TEN, BigDecimal.ONE, parcelas
            );
            SimulacaoResponses.CriarSimulacaoResponse r2 = new SimulacaoResponses.CriarSimulacaoResponse(
                    1L, BigDecimal.TEN, BigDecimal.ONE, parcelas
            );

            assertEquals(r1, r2, "Records com mesmos valores devem ser iguais");
            assertEquals(r1.hashCode(), r2.hashCode());
        }

        @Test
        @DisplayName("Instâncias com valores diferentes não devem ser iguais")
        void naoDeveSerIgualSeValoresDiferentes() {
            List<ParcelaResponses.ParcelaResponse> parcelas = List.of(criarParcelaMock());
            SimulacaoResponses.CriarSimulacaoResponse r1 = new SimulacaoResponses.CriarSimulacaoResponse(
                    1L, BigDecimal.TEN, BigDecimal.ONE, parcelas
            );
            SimulacaoResponses.CriarSimulacaoResponse r2 = new SimulacaoResponses.CriarSimulacaoResponse(
                    2L, BigDecimal.TEN, BigDecimal.ONE, parcelas
            );

            assertNotEquals(r1, r2);
        }
    }

    @Nested
    @DisplayName("BuscarSimulacaoResponse")
    class BuscarSimulacaoResponseTest {

        @Test
        @DisplayName("Deve criar BuscarSimulacaoResponse com todos os campos preenchidos corretamente")
        void deveCriarComTodosOsCampos() {
            Long id = 1L;
            BigDecimal valorInicial = BigDecimal.valueOf(1000);
            double taxaJurosMensal = 1.2;
            Integer prazoMeses = 12;
            BigDecimal valorTotalFinal = BigDecimal.valueOf(2000);
            BigDecimal valorTotalJuros = BigDecimal.valueOf(1000);
            List<ParcelaResponses.ParcelaResponse> parcelas = List.of(criarParcelaMock());

            SimulacaoResponses.BuscarSimulacaoResponse response = new SimulacaoResponses.BuscarSimulacaoResponse(
                    id, valorInicial, taxaJurosMensal, prazoMeses, valorTotalFinal, valorTotalJuros, parcelas
            );

            assertAll("response criada via construtor",
                    () -> assertEquals(id, response.id()),
                    () -> assertEquals(valorInicial, response.valorInicial()),
                    () -> assertEquals(taxaJurosMensal, response.taxaJurosMensal()),
                    () -> assertEquals(prazoMeses, response.prazoMeses()),
                    () -> assertEquals(valorTotalFinal, response.valorTotalFinal()),
                    () -> assertEquals(valorTotalJuros, response.valorTotalJuros()),
                    () -> assertEquals(1, response.parcelas().size())
            );
        }

        @Test
        @DisplayName("Deve aceitar valores nulos para campos não primitivos (erros/borda)")
        void deveAceitarValoresNulos() {
            assertDoesNotThrow(() -> {
                SimulacaoResponses.BuscarSimulacaoResponse response = new SimulacaoResponses.BuscarSimulacaoResponse(
                        null, null, 0.0, null, null, null, null
                );
                assertNull(response.id());
                assertNull(response.valorInicial());
                assertNull(response.prazoMeses());
                assertNull(response.valorTotalFinal());
                assertNull(response.valorTotalJuros());
                assertNull(response.parcelas());
            }, "Record deve aceitar campos nulos para objetos");
        }

        @Test
        @DisplayName("Deve aceitar lista de parcelas vazia (borda)")
        void deveAceitarListaDeParcelasVazia() {
            SimulacaoResponses.BuscarSimulacaoResponse response = new SimulacaoResponses.BuscarSimulacaoResponse(
                    1L, BigDecimal.TEN, 1.0, 10, BigDecimal.TEN, BigDecimal.ONE, Collections.emptyList()
            );

            assertTrue(response.parcelas().isEmpty(), "Deveria aceitar e retornar uma lista vazia");
        }

        @Test
        @DisplayName("Duas instâncias com mesmos valores devem ser iguais e ter o mesmo hashCode")
        void deveSerIgualSeValoresIguais() {
            List<ParcelaResponses.ParcelaResponse> parcelas = List.of(criarParcelaMock());
            SimulacaoResponses.BuscarSimulacaoResponse r1 = new SimulacaoResponses.BuscarSimulacaoResponse(
                    1L, BigDecimal.TEN, 1.5, 12, BigDecimal.valueOf(100), BigDecimal.TEN, parcelas
            );
            SimulacaoResponses.BuscarSimulacaoResponse r2 = new SimulacaoResponses.BuscarSimulacaoResponse(
                    1L, BigDecimal.TEN, 1.5, 12, BigDecimal.valueOf(100), BigDecimal.TEN, parcelas
            );

            assertEquals(r1, r2, "Records com mesmos valores devem ser iguais");
            assertEquals(r1.hashCode(), r2.hashCode());
        }

        @Test
        @DisplayName("Instâncias com valores diferentes não devem ser iguais")
        void naoDeveSerIgualSeValoresDiferentes() {
            List<ParcelaResponses.ParcelaResponse> parcelas = List.of(criarParcelaMock());
            SimulacaoResponses.BuscarSimulacaoResponse r1 = new SimulacaoResponses.BuscarSimulacaoResponse(
                    1L, BigDecimal.TEN, 1.5, 12, BigDecimal.valueOf(100), BigDecimal.TEN, parcelas
            );
            SimulacaoResponses.BuscarSimulacaoResponse r2 = new SimulacaoResponses.BuscarSimulacaoResponse(
                    2L, BigDecimal.TEN, 1.5, 12, BigDecimal.valueOf(100), BigDecimal.TEN, parcelas
            );

            assertNotEquals(r1, r2);
        }
    }

    @Test
    @DisplayName("Teste da classe externa SimulacaoResponses")
    void testSimulacaoResponsesClass() {
        SimulacaoResponses simulacaoResponses = new SimulacaoResponses();
        assertNotNull(simulacaoResponses.toString());
    }
}
