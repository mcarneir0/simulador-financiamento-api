package io.github.mcarneir0.financiamento.simulador.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "parcelas")
public class Parcela extends PanacheEntity {

    private LocalDate mes;
    private BigDecimal saldoInicial;
    private BigDecimal valorJuros;
    private BigDecimal saldoFinal;

    protected Parcela() { }

    public Parcela(
            LocalDate mes,
            BigDecimal saldoInicial,
            BigDecimal valorJuros,
            BigDecimal saldoFinal
    ) {
        this.mes = mes;
        this.saldoInicial = saldoInicial;
        this.valorJuros = valorJuros;
        this.saldoFinal = saldoFinal;
    }

    public LocalDate getMes() {
        return mes;
    }

     public void setMes(LocalDate mes) {
        this.mes = mes;
    }

     public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

     public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

     public BigDecimal getValorJuros() {
        return valorJuros;
    }

     public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

     public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

     public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
}
