package com.carlosnazario.hackathon.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Parcela implements Serializable {
    private int numero;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorPrestacao;

    public Parcela() {
    }

    public Parcela(int numero, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorPrestacao) {
        this.numero = numero;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorPrestacao = valorPrestacao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public BigDecimal getValorAmortizacao() {
        return valorAmortizacao;
    }

    public void setValorAmortizacao(BigDecimal valorAmortizacao) {
        this.valorAmortizacao = valorAmortizacao;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public BigDecimal getValorPrestacao() {
        return valorPrestacao;
    }

    public void setValorPrestacao(BigDecimal valorPrestacao) {
        this.valorPrestacao = valorPrestacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcela parcela = (Parcela) o;
        return numero == parcela.numero && Objects.equals(valorAmortizacao, parcela.valorAmortizacao) && Objects.equals(valorJuros, parcela.valorJuros) && Objects.equals(valorPrestacao, parcela.valorPrestacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, valorAmortizacao, valorJuros, valorPrestacao);
    }

    @Override
    public String toString() {
        return "Parcela{" +
                "numero=" + numero +
                ", valorAmortizacao=" + valorAmortizacao +
                ", valorJuros=" + valorJuros +
                ", valorPrestacao=" + valorPrestacao +
                '}';
    }
}