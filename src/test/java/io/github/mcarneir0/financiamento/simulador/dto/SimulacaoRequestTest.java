package io.github.mcarneir0.financiamento.simulador.dto;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("SimulacaoRequest - Testes de unidade")
public class SimulacaoRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Construtor e Valores (Cenários Felizes)")
    class ConstrutorEValores {

        @Test
        @DisplayName("Deve criar CriarSimulacaoRequest com todos os campos preenchidos corretamente")
        void deveCriarRequestComTodosOsCampos() {
            BigDecimal valorInicial = BigDecimal.valueOf(1000);
            double taxaJuros = 1.2;
            Integer prazo = 12;

            SimulacaoRequests.CriarSimulacaoRequest request = new SimulacaoRequests.CriarSimulacaoRequest(
                    valorInicial, taxaJuros, prazo
            );

            assertAll("request criada via construtor",
                    () -> assertEquals(valorInicial, request.valorInicial(), "valorInicial incorreto"),
                    () -> assertEquals(taxaJuros, request.taxaJurosMensal(), "taxaJurosMensal incorreta"),
                    () -> assertEquals(prazo, request.prazoMeses(), "prazoMeses incorreto")
            );
        }

        @Test
        @DisplayName("Validação deve passar com valores no limite aceitável (borda inferior)")
        void devePassarValidacaoComValoresMinimos() {
            SimulacaoRequests.CriarSimulacaoRequest request = new SimulacaoRequests.CriarSimulacaoRequest(
                    BigDecimal.ONE, 0.0, 1
            );
            
            Set<ConstraintViolation<SimulacaoRequests.CriarSimulacaoRequest>> violations = validator.validate(request);
            assertTrue(violations.isEmpty(), "Não deveria haver violações para valores no limite mínimo aceitável");
        }
    }

    @Nested
    @DisplayName("Validações de Constraints (Erros/Borda)")
    class ValidacoesDeConstraints {

        @Test
        @DisplayName("Deve falhar na validação se valorInicial for menor que 1")
        void deveFalharSeValorInicialMenorQueUm() {
            SimulacaoRequests.CriarSimulacaoRequest request = new SimulacaoRequests.CriarSimulacaoRequest(
                    BigDecimal.ZERO, 1.2, 12
            );

            Set<ConstraintViolation<SimulacaoRequests.CriarSimulacaoRequest>> violations = validator.validate(request);
            
            assertFalse(violations.isEmpty(), "Deveria falhar a validação (Min=1)");
            boolean temErroNoValorInicial = violations.stream()
                    .anyMatch(v -> v.getPropertyPath().toString().equals("valorInicial"));
            assertTrue(temErroNoValorInicial, "Deveria acusar erro no campo valorInicial");
        }

        @Test
        @DisplayName("Deve falhar na validação se prazoMeses for menor que 1")
        void deveFalharSePrazoMesesMenorQueUm() {
            SimulacaoRequests.CriarSimulacaoRequest request = new SimulacaoRequests.CriarSimulacaoRequest(
                    BigDecimal.valueOf(1000), 1.2, 0
            );

            Set<ConstraintViolation<SimulacaoRequests.CriarSimulacaoRequest>> violations = validator.validate(request);
            
            assertFalse(violations.isEmpty(), "Deveria falhar a validação (Min=1)");
            boolean temErroNoPrazo = violations.stream()
                    .anyMatch(v -> v.getPropertyPath().toString().equals("prazoMeses"));
            assertTrue(temErroNoPrazo, "Deveria acusar erro no campo prazoMeses");
        }

        @Test
        @DisplayName("Deve falhar na validação se valorInicial for nulo")
        void deveFalharSeValorInicialNulo() {
            SimulacaoRequests.CriarSimulacaoRequest request = new SimulacaoRequests.CriarSimulacaoRequest(
                    null, 1.2, 12
            );

            Set<ConstraintViolation<SimulacaoRequests.CriarSimulacaoRequest>> violations = validator.validate(request);
            assertFalse(violations.isEmpty(), "Deveria falhar a validação (@NotNull)");
        }

        @Test
        @DisplayName("Deve falhar na validação se prazoMeses for nulo")
        void deveFalharSePrazoMesesNulo() {
            SimulacaoRequests.CriarSimulacaoRequest request = new SimulacaoRequests.CriarSimulacaoRequest(
                    BigDecimal.valueOf(1000), 1.2, null
            );

            Set<ConstraintViolation<SimulacaoRequests.CriarSimulacaoRequest>> violations = validator.validate(request);
            assertFalse(violations.isEmpty(), "Deveria falhar a validação (@NotNull)");
        }
    }

    @Nested
    @DisplayName("Comportamento do Record")
    class ComportamentoDoRecord {

        @Test
        @DisplayName("Duas instâncias com mesmos valores devem ser iguais e ter o mesmo hashCode")
        void deveSerIgualSeValoresIguais() {
            BigDecimal vi = BigDecimal.valueOf(1000);
            double tj = 1.2;
            Integer pm = 12;

            SimulacaoRequests.CriarSimulacaoRequest r1 = new SimulacaoRequests.CriarSimulacaoRequest(vi, tj, pm);
            SimulacaoRequests.CriarSimulacaoRequest r2 = new SimulacaoRequests.CriarSimulacaoRequest(vi, tj, pm);

            assertEquals(r1, r2, "Records com mesmos valores devem ser iguais");
            assertEquals(r1.hashCode(), r2.hashCode(), "Records iguais devem ter o mesmo hashCode");
        }

        @Test
        @DisplayName("Instâncias com valores diferentes não devem ser iguais")
        void naoDeveSerIgualSeValoresDiferentes() {
            SimulacaoRequests.CriarSimulacaoRequest r1 = new SimulacaoRequests.CriarSimulacaoRequest(
                    BigDecimal.valueOf(1000), 1.2, 12
            );
            SimulacaoRequests.CriarSimulacaoRequest r2 = new SimulacaoRequests.CriarSimulacaoRequest(
                    BigDecimal.valueOf(2000), 1.2, 12
            );

            assertNotEquals(r1, r2, "Records com valores diferentes não devem ser iguais");
        }
    }

    @Test
    @DisplayName("Teste da classe externa SimulacaoRequests")
    void testSimulacaoRequestsClass() {
        SimulacaoRequests simulacaoRequests = new SimulacaoRequests();
        assertNotNull(simulacaoRequests.toString());
    }
}
