package io.github.mcarneir0.financiamento.simulador.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("ParcelaResponse - Testes de unidade")
public class ParcelaResponseTest {

    @Nested
    @DisplayName("Construtor e Valores (Cenários Felizes)")
    class ConstrutorEValores {

        @Test
        @DisplayName("Deve criar ParcelaResponse com todos os campos preenchidos corretamente")
        void deveCriarParcelaResponseComTodosOsCampos() {
            LocalDate mes = LocalDate.of(2026, 6, 1);
            BigDecimal saldoInicial = BigDecimal.valueOf(1000);
            BigDecimal valorJuros = BigDecimal.valueOf(50);
            BigDecimal saldoFinal = BigDecimal.valueOf(1050);

            ParcelaResponses.ParcelaResponse response = new ParcelaResponses.ParcelaResponse(
                    mes, saldoInicial, valorJuros, saldoFinal
            );

            assertAll("response criada via construtor",
                    () -> assertEquals(mes, response.mes(), "mes incorreto"),
                    () -> assertEquals(saldoInicial, response.saldoInicial(), "saldoInicial incorreto"),
                    () -> assertEquals(valorJuros, response.valorJuros(), "valorJuros incorreto"),
                    () -> assertEquals(saldoFinal, response.saldoFinal(), "saldoFinal incorreto")
            );
        }

        @Test
        @DisplayName("Deve aceitar valores zero (borda inferior)")
        void deveAceitarValoresZero() {
            ParcelaResponses.ParcelaResponse response = new ParcelaResponses.ParcelaResponse(
                    LocalDate.now(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );

            assertAll("response com zeros",
                    () -> assertEquals(BigDecimal.ZERO, response.saldoInicial(), "saldoInicial deveria ser zero"),
                    () -> assertEquals(BigDecimal.ZERO, response.valorJuros(), "valorJuros deveria ser zero"),
                    () -> assertEquals(BigDecimal.ZERO, response.saldoFinal(), "saldoFinal deveria ser zero")
            );
        }

        @Test
        @DisplayName("Deve aceitar valores negativos (borda de negatividade)")
        void deveAceitarValoresNegativos() {
            BigDecimal negativo = BigDecimal.valueOf(-1);
            ParcelaResponses.ParcelaResponse response = new ParcelaResponses.ParcelaResponse(
                    LocalDate.now(), negativo, negativo, negativo
            );

            assertAll("valores negativos",
                    () -> assertEquals(negativo, response.saldoInicial(), "saldoInicial negativo deveria ser aceito"),
                    () -> assertEquals(negativo, response.valorJuros(), "valorJuros negativo deveria ser aceito"),
                    () -> assertEquals(negativo, response.saldoFinal(), "saldoFinal negativo deveria ser aceito")
            );
        }

        @Test
        @DisplayName("Deve aceitar valores BigDecimal com alta precisão (borda de escala)")
        void deveAceitarValoresComAltaPrecisao() {
            BigDecimal altaPrecisao = new BigDecimal("1234567890.123456789");
            ParcelaResponses.ParcelaResponse response = new ParcelaResponses.ParcelaResponse(
                    LocalDate.now(), altaPrecisao, altaPrecisao, altaPrecisao
            );

            assertEquals(0, altaPrecisao.compareTo(response.saldoInicial()),
                    "Valor de alta precisão deveria ser preservado");
        }
    }

    @Nested
    @DisplayName("Campos Nulos (Erros/Borda)")
    class CamposNulos {

        @Test
        @DisplayName("Deve aceitar campos nulos no construtor do record")
        void deveAceitarCamposNulos() {
            assertDoesNotThrow(() -> {
                ParcelaResponses.ParcelaResponse response = new ParcelaResponses.ParcelaResponse(
                        null, null, null, null
                );
                assertNull(response.mes());
                assertNull(response.saldoInicial());
                assertNull(response.valorJuros());
                assertNull(response.saldoFinal());
            }, "O record deve aceitar campos nulos, a menos que validado explicitamente");
        }
    }

    @Nested
    @DisplayName("Comportamento do Record")
    class ComportamentoDoRecord {

        @Test
        @DisplayName("Duas instâncias com mesmos valores devem ser iguais e ter o mesmo hashCode")
        void deveSerIgualSeValoresIguais() {
            LocalDate mes = LocalDate.of(2026, 6, 1);;
            BigDecimal si = BigDecimal.valueOf(1000);
            BigDecimal vj = BigDecimal.valueOf(10);
            BigDecimal sf = BigDecimal.valueOf(990);

            ParcelaResponses.ParcelaResponse r1 = new ParcelaResponses.ParcelaResponse(mes, si, vj, sf);
            ParcelaResponses.ParcelaResponse r2 = new ParcelaResponses.ParcelaResponse(mes, si, vj, sf);

            assertEquals(r1, r2, "Records com mesmos valores devem ser iguais");
            assertEquals(r1.hashCode(), r2.hashCode(), "Records iguais devem ter o mesmo hashCode");
        }

        @Test
        @DisplayName("Instâncias com valores diferentes não devem ser iguais")
        void naoDeveSerIgualSeValoresDiferentes() {
            ParcelaResponses.ParcelaResponse r1 = new ParcelaResponses.ParcelaResponse(
                    LocalDate.now(), BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO
            );
            ParcelaResponses.ParcelaResponse r2 = new ParcelaResponses.ParcelaResponse(
                    LocalDate.now().plusDays(1), BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO
            );

            assertNotEquals(r1, r2, "Records com valores diferentes não devem ser iguais");
        }
    }

    @Test
    @DisplayName("Teste da classe externa ParcelaResponses")
    void testParcelaResponsesClass() {
        ParcelaResponses parcelaResponses = new ParcelaResponses();
        assertNotNull(parcelaResponses.toString());
    }
}
