package io.github.mcarneir0.financiamento.simulador.service;

import io.github.mcarneir0.financiamento.simulador.entity.Parcela;
import io.github.mcarneir0.financiamento.simulador.entity.Simulacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Transactional
    public Simulacao calcularSimulacao(
            BigDecimal valorInicial,
            double taxaJurosMensal,
            Integer prazoMeses
    ) {

        BigDecimal saldoAnterior = BigDecimal.ZERO;
        taxaJurosMensal = taxaJurosMensal / 100;
        List<Parcela> parcelas = new ArrayList<>();
        LocalDate dataInicial = LocalDate.now();

        for (int i = 1; i <= prazoMeses; i++) {
            BigDecimal valorFinal = valorInicial.multiply(BigDecimal.valueOf(Math.pow((1 + taxaJurosMensal), i)));
            BigDecimal juros = valorFinal.subtract(valorInicial).subtract(saldoAnterior);
            saldoAnterior = saldoAnterior.add(juros);

            parcelas.add(new Parcela(
                    dataInicial.plusMonths(i),
                    valorFinal.subtract(juros),
                    juros,
                    valorFinal
            ));
        }

        BigDecimal valorTotalJuros = BigDecimal.ZERO;
        for(Parcela parcela : parcelas) {
            valorTotalJuros = valorTotalJuros.add(parcela.getValorJuros());
        }

        Simulacao simulacao = new Simulacao(
                valorInicial,
                parcelas.getLast().getSaldoFinal(),
                valorTotalJuros,
                taxaJurosMensal,
                prazoMeses,
                parcelas
        );

        simulacao.persist();
        return simulacao;
    }
}
