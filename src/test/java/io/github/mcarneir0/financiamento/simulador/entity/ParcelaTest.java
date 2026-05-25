package io.github.mcarneir0.financiamento.simulador.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("Parcela - Testes de unidade")
public class ParcelaTest {

    // -------------------------------------------------------------------------
    // Cenários felizes — construtor com argumentos
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Construtor com argumentos")
    class ConstrutorComArgumentos {

        @Test
        @DisplayName("Deve criar parcela com todos os campos preenchidos corretamente")
        void deveCriarParcelaComTodosOsCampos() {
            LocalDate mes = LocalDate.of(2025, 1, 1);
            BigDecimal saldoInicial = BigDecimal.valueOf(1000);
            BigDecimal valorJuros   = BigDecimal.valueOf(10);
            BigDecimal saldoFinal   = BigDecimal.valueOf(990);

            Parcela parcela = new Parcela(mes, saldoInicial, valorJuros, saldoFinal);

            assertAll("parcela criada via construtor",
                () -> assertEquals(mes,          parcela.getMes(),          "mes incorreto"),
                () -> assertEquals(saldoInicial, parcela.getSaldoInicial(), "saldoInicial incorreto"),
                () -> assertEquals(valorJuros,   parcela.getValorJuros(),   "valorJuros incorreto"),
                () -> assertEquals(saldoFinal,   parcela.getSaldoFinal(),   "saldoFinal incorreto")
            );
        }

        @Test
        @DisplayName("Deve criar parcela com valores zero (borda inferior)")
        void deveCriarParcelaComValoresZero() {
            Parcela parcela = new Parcela(
                LocalDate.now(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
            );

            assertAll("parcela com zeros",
                () -> assertEquals(BigDecimal.ZERO, parcela.getSaldoInicial(), "saldoInicial deveria ser zero"),
                () -> assertEquals(BigDecimal.ZERO, parcela.getValorJuros(),   "valorJuros deveria ser zero"),
                () -> assertEquals(BigDecimal.ZERO, parcela.getSaldoFinal(),   "saldoFinal deveria ser zero")
            );
        }

        @Test
        @DisplayName("Deve aceitar data passada como mês da parcela (borda)")
        void deveAceitarDataPassada() {
            LocalDate dataPassada = LocalDate.of(2000, 1, 1);
            Parcela parcela = new Parcela(dataPassada, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN);

            assertEquals(dataPassada, parcela.getMes(), "data passada deveria ser aceita");
        }

        @Test
        @DisplayName("Deve aceitar data futura como mês da parcela (borda)")
        void deveAceitarDataFutura() {
            LocalDate dataFutura = LocalDate.now().plusYears(30);
            Parcela parcela = new Parcela(dataFutura, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN);

            assertEquals(dataFutura, parcela.getMes(), "data futura deveria ser aceita");
        }

        @Test
        @DisplayName("Deve aceitar valores negativos nos saldos (borda de negatividade)")
        void deveAceitarValoresNegativos() {
            BigDecimal negativo = BigDecimal.valueOf(-1);
            Parcela parcela = new Parcela(LocalDate.now(), negativo, negativo, negativo);

            assertAll("valores negativos",
                () -> assertEquals(negativo, parcela.getSaldoInicial(), "saldoInicial negativo deveria ser aceito"),
                () -> assertEquals(negativo, parcela.getValorJuros(),   "valorJuros negativo deveria ser aceito"),
                () -> assertEquals(negativo, parcela.getSaldoFinal(),   "saldoFinal negativo deveria ser aceito")
            );
        }

        @Test
        @DisplayName("Deve aceitar valores BigDecimal com alta precisão (borda de escala)")
        void deveAceitarValoresComAltaPrecisao() {
            BigDecimal altaPrecisao = new BigDecimal("1234567890.123456789");
            Parcela parcela = new Parcela(LocalDate.now(), altaPrecisao, altaPrecisao, altaPrecisao);

            assertEquals(0, altaPrecisao.compareTo(parcela.getSaldoInicial()),
                "Valor de alta precisão deveria ser preservado em saldoInicial");
        }
    }

    // -------------------------------------------------------------------------
    // Cenários felizes — setters / getters
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Setters e Getters")
    class SettersEGetters {

        @Test
        @DisplayName("Deve atualizar o mês via setter")
        void deveAtualizarMes() {
            Parcela parcela = parcelaBase();
            LocalDate novaData = LocalDate.of(2030, 6, 15);
            parcela.setMes(novaData);

            assertEquals(novaData, parcela.getMes(), "setMes não atualizou o campo corretamente");
        }

        @Test
        @DisplayName("Deve atualizar o saldoInicial via setter")
        void deveAtualizarSaldoInicial() {
            Parcela parcela = parcelaBase();
            BigDecimal novoSaldo = BigDecimal.valueOf(5000);
            parcela.setSaldoInicial(novoSaldo);

            assertEquals(novoSaldo, parcela.getSaldoInicial(), "setSaldoInicial não atualizou o campo corretamente");
        }

        @Test
        @DisplayName("Deve atualizar o valorJuros via setter")
        void deveAtualizarValorJuros() {
            Parcela parcela = parcelaBase();
            BigDecimal novoJuros = BigDecimal.valueOf(99.99);
            parcela.setValorJuros(novoJuros);

            assertEquals(novoJuros, parcela.getValorJuros(), "setValorJuros não atualizou o campo corretamente");
        }

        @Test
        @DisplayName("Deve atualizar o saldoFinal via setter")
        void deveAtualizarSaldoFinal() {
            Parcela parcela = parcelaBase();
            BigDecimal novoFinal = BigDecimal.valueOf(4900);
            parcela.setSaldoFinal(novoFinal);

            assertEquals(novoFinal, parcela.getSaldoFinal(), "setSaldoFinal não atualizou o campo corretamente");
        }
    }

    // -------------------------------------------------------------------------
    // Cenários de borda — campos nulos
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Campos nulos (borda/erro)")
    class CamposNulos {

        @Test
        @DisplayName("Deve aceitar mês nulo via construtor sem lançar exceção")
        void deveAceitarMesNuloViaConstutor() {
            assertDoesNotThrow(
                () -> new Parcela(null, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN),
                "Construtor não deveria lançar exceção ao receber mes=null"
            );
        }

        @Test
        @DisplayName("Deve retornar null quando mês não foi atribuído")
        void deveRetornarNullParaMesNaoAtribuido() {
            Parcela parcela = new Parcela(null, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN);

            assertNull(parcela.getMes(), "getMes() deveria retornar null quando não foi definido");
        }

        @Test
        @DisplayName("Deve aceitar saldoInicial nulo sem lançar exceção")
        void deveAceitarSaldoInicialNulo() {
            assertDoesNotThrow(
                () -> new Parcela(LocalDate.now(), null, BigDecimal.ONE, BigDecimal.TEN),
                "Construtor não deveria lançar exceção ao receber saldoInicial=null"
            );
        }

        @Test
        @DisplayName("Deve aceitar valorJuros nulo sem lançar exceção")
        void deveAceitarValorJurosNulo() {
            assertDoesNotThrow(
                () -> new Parcela(LocalDate.now(), BigDecimal.TEN, null, BigDecimal.TEN),
                "Construtor não deveria lançar exceção ao receber valorJuros=null"
            );
        }

        @Test
        @DisplayName("Deve aceitar saldoFinal nulo sem lançar exceção")
        void deveAceitarSaldoFinalNulo() {
            assertDoesNotThrow(
                () -> new Parcela(LocalDate.now(), BigDecimal.TEN, BigDecimal.ONE, null),
                "Construtor não deveria lançar exceção ao receber saldoFinal=null"
            );
        }

        @Test
        @DisplayName("Deve aceitar null via setter de mês sem lançar exceção")
        void deveAceitarNullNoSetterMes() {
            Parcela parcela = parcelaBase();
            assertDoesNotThrow(() -> parcela.setMes(null), "setMes(null) não deveria lançar exceção");
            assertNull(parcela.getMes());
        }

        @Test
        @DisplayName("Deve aceitar null via setter de saldoInicial sem lançar exceção")
        void deveAceitarNullNoSetterSaldoInicial() {
            Parcela parcela = parcelaBase();
            assertDoesNotThrow(() -> parcela.setSaldoInicial(null), "setSaldoInicial(null) não deveria lançar exceção");
            assertNull(parcela.getSaldoInicial());
        }
    }

    // -------------------------------------------------------------------------
    // Cenários de borda — consistência lógica (sem persistência)
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Consistência lógica dos valores")
    class ConsistenciaLogica {

        @Test
        @DisplayName("saldoFinal deve ser menor que saldoInicial quando há amortização positiva")
        void saldoFinalDeveSerMenorQueSaldoInicialComAmortizacao() {
            BigDecimal saldoInicial = BigDecimal.valueOf(1000);
            BigDecimal saldoFinal   = BigDecimal.valueOf(900);

            Parcela parcela = new Parcela(LocalDate.now(), saldoInicial, BigDecimal.valueOf(10), saldoFinal);

            assertTrue(parcela.getSaldoFinal().compareTo(parcela.getSaldoInicial()) < 0,
                "saldoFinal deveria ser menor que saldoInicial em uma parcela com amortização");
        }

        @Test
        @DisplayName("saldoFinal igual a saldoInicial representa parcela sem amortização (borda)")
        void saldoFinalIgualSaldoInicialSemAmortizacao() {
            BigDecimal saldo = BigDecimal.valueOf(1000);
            Parcela parcela = new Parcela(LocalDate.now(), saldo, BigDecimal.ZERO, saldo);

            assertEquals(0, parcela.getSaldoFinal().compareTo(parcela.getSaldoInicial()),
                "Sem amortização, saldoFinal e saldoInicial devem ser iguais");
        }

        @Test
        @DisplayName("Duas instâncias com mesmos valores devem ter campos idênticos")
        void duasInstanciasComMesmosValores() {
            LocalDate mes = LocalDate.of(2025, 3, 1);
            BigDecimal si = BigDecimal.valueOf(500);
            BigDecimal vj = BigDecimal.valueOf(5);
            BigDecimal sf = BigDecimal.valueOf(495);

            Parcela p1 = new Parcela(mes, si, vj, sf);
            Parcela p2 = new Parcela(mes, si, vj, sf);

            assertAll("instâncias independentes com valores iguais",
                () -> assertEquals(p1.getMes(),          p2.getMes()),
                () -> assertEquals(p1.getSaldoInicial(), p2.getSaldoInicial()),
                () -> assertEquals(p1.getValorJuros(),   p2.getValorJuros()),
                () -> assertEquals(p1.getSaldoFinal(),   p2.getSaldoFinal())
            );
        }

        @Test
        @DisplayName("Alteração em uma parcela não deve afetar outra (independência de instâncias)")
        void alteracaoNaoDeveAfetrOutraInstancia() {
            BigDecimal saldoOriginal = BigDecimal.valueOf(1000);
            Parcela p1 = new Parcela(LocalDate.now(), saldoOriginal, BigDecimal.TEN, BigDecimal.valueOf(990));
            Parcela p2 = new Parcela(LocalDate.now(), saldoOriginal, BigDecimal.TEN, BigDecimal.valueOf(990));

            p1.setSaldoInicial(BigDecimal.valueOf(9999));

            assertEquals(saldoOriginal, p2.getSaldoInicial(),
                "Alteração em p1 não deveria afetar p2 (instâncias independentes)");
        }
    }

    // -------------------------------------------------------------------------
    // Utilitários
    // -------------------------------------------------------------------------

    private Parcela parcelaBase() {
        return new Parcela(
            LocalDate.of(2026, 6, 1),
            BigDecimal.valueOf(1000),
            BigDecimal.valueOf(10),
            BigDecimal.valueOf(990)
        );
    }
}
