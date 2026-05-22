package io.github.mcarneir0.financiamento.simulador.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "simulacoes")
public class Simulacao extends PanacheEntity {

    private BigDecimal valorInicial;
    private BigDecimal valorTotalFinal;
    private BigDecimal valorTotalJuros;
    private double taxaJurosMensal;
    private Integer prazoMeses;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parcela> parcelas;

    protected Simulacao() { }

    public Simulacao(
            BigDecimal valorInicial,
            BigDecimal valorTotalFinal,
            BigDecimal valorTotalJuros,
            double taxaJurosMensal,
            Integer prazoMeses,
            List<Parcela> parcelas
    ) {
        this.valorInicial = valorInicial;
        this.valorTotalFinal = valorTotalFinal;
        this.valorTotalJuros = valorTotalJuros;
        this.taxaJurosMensal = taxaJurosMensal;
        this.prazoMeses = prazoMeses;
        this.parcelas = parcelas;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public BigDecimal getValorTotalFinal() {
        return valorTotalFinal;
    }

    public void setValorTotalFinal(BigDecimal valorTotalFinal) {
        this.valorTotalFinal = valorTotalFinal;
    }

    public BigDecimal getValorTotalJuros() {
        return valorTotalJuros;
    }

    public void setValorTotalJuros(BigDecimal valorTotalJuros) {
        this.valorTotalJuros = valorTotalJuros;
    }

    public double getTaxaJurosMensal() {
        return taxaJurosMensal;
    }

    public void setTaxaJurosMensal(double taxaJurosMensal) {
        this.taxaJurosMensal = taxaJurosMensal;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }
}
