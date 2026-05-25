package io.github.mcarneir0.financiamento.simulador.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("Simulacao - Testes de unidade")
public class SimulacaoTest {

    // -------------------------------------------------------------------------
    // Cenários felizes — construtor com argumentos
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Construtor com argumentos")
    class ConstrutorComArgumentos {

        @Test
        @DisplayName("Deve criar simulação com todos os campos preenchidos corretamente")
        void deveCriarSimulacaoComTodosOsCampos() {
            BigDecimal valorInicial = BigDecimal.valueOf(1000);
            BigDecimal valorTotalFinal = BigDecimal.valueOf(1126.83);
            BigDecimal valorTotalJuros = BigDecimal.valueOf(126.83);
            double taxaJurosMensal = 1.0;
            Integer prazoMeses = 12;
            List<Parcela> parcelas = List.of(parcelaBase());

            Simulacao simulacao = new Simulacao(
                    valorInicial, valorTotalFinal, valorTotalJuros,
                    taxaJurosMensal, prazoMeses, parcelas);

            assertAll("simulacao criada via construtor",
                    () -> assertEquals(valorInicial, simulacao.getValorInicial(), "valorInicial incorreto"),
                    () -> assertEquals(valorTotalFinal, simulacao.getValorTotalFinal(), "valorTotalFinal incorreto"),
                    () -> assertEquals(valorTotalJuros, simulacao.getValorTotalJuros(), "valorTotalJuros incorreto"),
                    () -> assertEquals(taxaJurosMensal, simulacao.getTaxaJurosMensal(), "taxaJurosMensal incorreto"),
                    () -> assertEquals(prazoMeses, simulacao.getPrazoMeses(), "prazoMeses incorreto"),
                    () -> assertEquals(1, simulacao.getParcelas().size(), "tamanho da lista de parcelas incorreto"));
        }

        @Test
        @DisplayName("Deve criar simulação com lista de parcelas vazia (borda inferior da lista)")
        void deveCriarSimulacaoComListaVazia() {
            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.ZERO,
                    0.0, 0, Collections.emptyList());

            assertNotNull(simulacao.getParcelas(), "Lista de parcelas não deve ser null");
            assertTrue(simulacao.getParcelas().isEmpty(), "Lista de parcelas deve estar vazia");
        }

        @Test
        @DisplayName("Deve criar simulação com múltiplas parcelas")
        void deveCriarSimulacaoComMultiplasParcelas() {
            List<Parcela> parcelas = List.of(parcelaBase(), parcelaBase(), parcelaBase());

            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(3000), BigDecimal.valueOf(3300), BigDecimal.valueOf(300),
                    1.0, 3, parcelas);

            assertEquals(3, simulacao.getParcelas().size(), "Deve ter exatamente 3 parcelas");
        }

        @Test
        @DisplayName("Deve aceitar taxa de juros zero (borda — sem juros)")
        void deveAceitarTaxaJurosZero() {
            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.ZERO,
                    0.0, 12, List.of(parcelaBase()));

            assertEquals(0.0, simulacao.getTaxaJurosMensal(), 1e-9, "Taxa de juros zero deveria ser aceita");
        }

        @Test
        @DisplayName("Deve aceitar prazo de um mês (borda inferior do prazo)")
        void deveAceitarPrazoDeUmMes() {
            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(1000), BigDecimal.valueOf(1010), BigDecimal.TEN,
                    1.0, 1, List.of(parcelaBase()));

            assertEquals(1, simulacao.getPrazoMeses(), "Prazo de 1 mês deveria ser aceito");
        }

        @Test
        @DisplayName("Deve aceitar prazo elevado (borda superior do prazo — 360 meses / 30 anos)")
        void deveAceitarPrazoElevado() {
            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(500_000), BigDecimal.valueOf(900_000), BigDecimal.valueOf(400_000),
                    0.75, 360, new ArrayList<>());

            assertEquals(360, simulacao.getPrazoMeses(), "Prazo de 360 meses deveria ser aceito");
        }

        @Test
        @DisplayName("Deve aceitar valores BigDecimal com alta precisão")
        void deveAceitarValoresComAltaPrecisao() {
            BigDecimal altaPrecisao = new BigDecimal("123456789.987654321");
            Simulacao simulacao = new Simulacao(
                    altaPrecisao, altaPrecisao, altaPrecisao, 0.5, 12, List.of());

            assertEquals(0, altaPrecisao.compareTo(simulacao.getValorInicial()),
                    "Valor de alta precisão deveria ser preservado em valorInicial");
        }
    }

    // -------------------------------------------------------------------------
    // Cenários felizes — setters / getters
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Setters e Getters")
    class SettersEGetters {

        @Test
        @DisplayName("Deve atualizar valorInicial via setter")
        void deveAtualizarValorInicial() {
            Simulacao simulacao = simulacaoBase();
            BigDecimal novo = BigDecimal.valueOf(5000);
            simulacao.setValorInicial(novo);

            assertEquals(novo, simulacao.getValorInicial(), "setValorInicial não atualizou corretamente");
        }

        @Test
        @DisplayName("Deve atualizar valorTotalFinal via setter")
        void deveAtualizarValorTotalFinal() {
            Simulacao simulacao = simulacaoBase();
            BigDecimal novo = BigDecimal.valueOf(5500);
            simulacao.setValorTotalFinal(novo);

            assertEquals(novo, simulacao.getValorTotalFinal(), "setValorTotalFinal não atualizou corretamente");
        }

        @Test
        @DisplayName("Deve atualizar valorTotalJuros via setter")
        void deveAtualizarValorTotalJuros() {
            Simulacao simulacao = simulacaoBase();
            BigDecimal novo = BigDecimal.valueOf(500);
            simulacao.setValorTotalJuros(novo);

            assertEquals(novo, simulacao.getValorTotalJuros(), "setValorTotalJuros não atualizou corretamente");
        }

        @Test
        @DisplayName("Deve atualizar taxaJurosMensal via setter")
        void deveAtualizarTaxaJurosMensal() {
            Simulacao simulacao = simulacaoBase();
            simulacao.setTaxaJurosMensal(2.5);

            assertEquals(2.5, simulacao.getTaxaJurosMensal(), 1e-9, "setTaxaJurosMensal não atualizou corretamente");
        }

        @Test
        @DisplayName("Deve atualizar prazoMeses via setter")
        void deveAtualizarPrazoMeses() {
            Simulacao simulacao = simulacaoBase();
            simulacao.setPrazoMeses(24);

            assertEquals(24, simulacao.getPrazoMeses(), "setPrazoMeses não atualizou corretamente");
        }

        @Test
        @DisplayName("Deve atualizar lista de parcelas via setter")
        void deveAtualizarListaDeParcelas() {
            Simulacao simulacao = simulacaoBase();
            List<Parcela> novaLista = List.of(parcelaBase(), parcelaBase());
            simulacao.setParcelas(novaLista);

            assertEquals(2, simulacao.getParcelas().size(), "setParcelas não atualizou a lista corretamente");
        }
    }

    // -------------------------------------------------------------------------
    // Cenários de borda — campos nulos
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Campos nulos (borda/erro)")
    class CamposNulos {

        @Test
        @DisplayName("Deve aceitar valorInicial nulo via construtor sem lançar exceção")
        void deveAceitarValorInicialNulo() {
            assertDoesNotThrow(
                    () -> new Simulacao(null, BigDecimal.TEN, BigDecimal.ONE, 1.0, 12, List.of()),
                    "Construtor não deveria lançar exceção ao receber valorInicial=null");
        }

        @Test
        @DisplayName("Deve retornar null para valorInicial quando não atribuído")
        void deveRetornarNullParaValorInicialNaoAtribuido() {
            Simulacao simulacao = new Simulacao(null, BigDecimal.TEN, BigDecimal.ZERO, 1.0, 12, List.of());

            assertNull(simulacao.getValorInicial(), "getValorInicial() deveria retornar null");
        }

        @Test
        @DisplayName("Deve aceitar valorTotalFinal nulo via construtor sem lançar exceção")
        void deveAceitarValorTotalFinalNulo() {
            assertDoesNotThrow(
                    () -> new Simulacao(BigDecimal.valueOf(1000), null, BigDecimal.TEN, 1.0, 12, List.of()),
                    "Construtor não deveria lançar exceção ao receber valorTotalFinal=null");
        }

        @Test
        @DisplayName("Deve aceitar valorTotalJuros nulo via construtor sem lançar exceção")
        void deveAceitarValorTotalJurosNulo() {
            assertDoesNotThrow(
                    () -> new Simulacao(BigDecimal.valueOf(1000), BigDecimal.valueOf(1100), null, 1.0, 12, List.of()),
                    "Construtor não deveria lançar exceção ao receber valorTotalJuros=null");
        }

        @Test
        @DisplayName("Deve aceitar lista de parcelas nula via construtor sem lançar exceção")
        void deveAceitarListaNulaViaConstutor() {
            assertDoesNotThrow(
                    () -> new Simulacao(BigDecimal.valueOf(1000), BigDecimal.valueOf(1100), BigDecimal.TEN, 1.0, 12,
                            null),
                    "Construtor não deveria lançar exceção ao receber parcelas=null");
        }

        @Test
        @DisplayName("Deve retornar null para parcelas quando não atribuído")
        void deveRetornarNullParaParcelasNaoAtribuidas() {
            Simulacao simulacao = new Simulacao(BigDecimal.valueOf(1000), BigDecimal.valueOf(1100), BigDecimal.TEN, 1.0,
                    12, null);

            assertNull(simulacao.getParcelas(), "getParcelas() deveria retornar null quando passado null");
        }

        @Test
        @DisplayName("Deve aceitar null via setter de valorInicial sem lançar exceção")
        void deveAceitarNullNoSetterValorInicial() {
            Simulacao simulacao = simulacaoBase();
            assertDoesNotThrow(() -> simulacao.setValorInicial(null),
                    "setValorInicial(null) não deveria lançar exceção");
            assertNull(simulacao.getValorInicial());
        }

        @Test
        @DisplayName("Deve aceitar null via setter de parcelas sem lançar exceção")
        void deveAceitarNullNoSetterParcelas() {
            Simulacao simulacao = simulacaoBase();
            assertDoesNotThrow(() -> simulacao.setParcelas(null), "setParcelas(null) não deveria lançar exceção");
            assertNull(simulacao.getParcelas());
        }
    }

    // -------------------------------------------------------------------------
    // Cenários de borda — consistência lógica
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("Consistência lógica dos valores")
    class ConsistenciaLogica {

        @Test
        @DisplayName("valorTotalFinal deve ser maior que valorInicial quando há juros positivos")
        void valorTotalFinalDeveSerMaiorQuandoHaJuros() {
            BigDecimal valorInicial = BigDecimal.valueOf(1000);
            BigDecimal valorTotalFinal = BigDecimal.valueOf(1100);

            Simulacao simulacao = new Simulacao(
                    valorInicial, valorTotalFinal, BigDecimal.valueOf(100), 1.0, 12, List.of());

            assertTrue(simulacao.getValorTotalFinal().compareTo(simulacao.getValorInicial()) > 0,
                    "valorTotalFinal deveria ser maior que valorInicial quando há juros");
        }

        @Test
        @DisplayName("valorTotalJuros deve ser igual à diferença entre valorTotalFinal e valorInicial (borda de integridade)")
        void valorTotalJurosDeveSerConsistente() {
            BigDecimal valorInicial = BigDecimal.valueOf(1000);
            BigDecimal valorTotalFinal = BigDecimal.valueOf(1100);
            BigDecimal valorTotalJuros = BigDecimal.valueOf(100);

            Simulacao simulacao = new Simulacao(
                    valorInicial, valorTotalFinal, valorTotalJuros, 1.0, 12, List.of());

            BigDecimal diferencaCalculada = simulacao.getValorTotalFinal()
                    .subtract(simulacao.getValorInicial());

            assertEquals(0, valorTotalJuros.compareTo(diferencaCalculada),
                    "valorTotalJuros deveria corresponder à diferença entre valorTotalFinal e valorInicial");
        }

        @Test
        @DisplayName("Número de parcelas deve corresponder ao prazoMeses (borda de integridade)")
        void numeroDeParccelasDeveCorresponderAoPrazoMeses() {
            int prazo = 3;
            List<Parcela> parcelas = List.of(parcelaBase(), parcelaBase(), parcelaBase());

            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(3000), BigDecimal.valueOf(3300), BigDecimal.valueOf(300),
                    1.0, prazo, parcelas);

            assertEquals(prazo, simulacao.getParcelas().size(),
                    "O número de parcelas deveria ser igual ao prazoMeses");
        }

        @Test
        @DisplayName("Duas instâncias com mesmos valores devem ter campos idênticos")
        void duasInstanciasComMesmosValores() {
            List<Parcela> p1List = List.of(parcelaBase());
            List<Parcela> p2List = List.of(parcelaBase());

            Simulacao s1 = new Simulacao(BigDecimal.valueOf(1000), BigDecimal.valueOf(1100), BigDecimal.valueOf(100),
                    1.0, 12, p1List);
            Simulacao s2 = new Simulacao(BigDecimal.valueOf(1000), BigDecimal.valueOf(1100), BigDecimal.valueOf(100),
                    1.0, 12, p2List);

            assertAll("instâncias independentes com valores iguais",
                    () -> assertEquals(s1.getValorInicial(), s2.getValorInicial()),
                    () -> assertEquals(s1.getValorTotalFinal(), s2.getValorTotalFinal()),
                    () -> assertEquals(s1.getValorTotalJuros(), s2.getValorTotalJuros()),
                    () -> assertEquals(s1.getTaxaJurosMensal(), s2.getTaxaJurosMensal(), 1e-9),
                    () -> assertEquals(s1.getPrazoMeses(), s2.getPrazoMeses()),
                    () -> assertEquals(s1.getParcelas().size(), s2.getParcelas().size()));
        }

        @Test
        @DisplayName("Alteração em uma simulação não deve afetar outra (independência de instâncias)")
        void alteracaoNaoDeveAfetatOutraInstancia() {
            BigDecimal valorOriginal = BigDecimal.valueOf(1000);
            Simulacao s1 = new Simulacao(valorOriginal, BigDecimal.valueOf(1100), BigDecimal.valueOf(100), 1.0, 12,
                    List.of());
            Simulacao s2 = new Simulacao(valorOriginal, BigDecimal.valueOf(1100), BigDecimal.valueOf(100), 1.0, 12,
                    List.of());

            s1.setValorInicial(BigDecimal.valueOf(9999));

            assertEquals(valorOriginal, s2.getValorInicial(),
                    "Alteração em s1 não deveria afetar s2 (instâncias independentes)");
        }

        @Test
        @DisplayName("Taxa de juros com valor extremamente alto deve ser aceita (borda superior)")
        void deveAceitarTaxaJurosAlta() {
            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(1000), BigDecimal.valueOf(999_000), BigDecimal.valueOf(998_000),
                    999.99, 1, List.of());

            assertEquals(999.99, simulacao.getTaxaJurosMensal(), 1e-9,
                    "Taxa de juros muito alta deveria ser aceita como valor de borda");
        }

        @Test
        @DisplayName("prazoMeses zero deve ser aceito como borda mínima")
        void deveAceitarPrazoZero() {
            Simulacao simulacao = new Simulacao(
                    BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.ZERO,
                    0.0, 0, List.of());

            assertEquals(0, simulacao.getPrazoMeses(), "prazoMeses zero deveria ser aceito");
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
                BigDecimal.valueOf(990));
    }

    private Simulacao simulacaoBase() {
        return new Simulacao(
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(1126.83),
                BigDecimal.valueOf(126.83),
                1.0,
                12,
                new ArrayList<>(List.of(parcelaBase())));
    }
}
