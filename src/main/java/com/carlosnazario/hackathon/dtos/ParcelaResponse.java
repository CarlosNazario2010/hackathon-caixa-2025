package com.carlosnazario.hackathon.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public class ParcelaResponse {
    private int numero;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorPrestacao;

    public ParcelaResponse() {
    }

    public ParcelaResponse(int numero, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorPrestacao) {
        this.numero = numero;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorPrestacao = valorPrestacao;
    }

    public int getNumero() {
        return numero;
    }

    public BigDecimal getValorAmortizacao() {
        return valorAmortizacao;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public BigDecimal getValorPrestacao() {
        return valorPrestacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelaResponse that = (ParcelaResponse) o;
        return numero == that.numero &&
                Objects.equals(valorAmortizacao, that.valorAmortizacao) &&
                Objects.equals(valorJuros, that.valorJuros) &&
                Objects.equals(valorPrestacao, that.valorPrestacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, valorAmortizacao, valorJuros, valorPrestacao);
    }
    @Override
    public String toString() {
        return "ParcelaResponse{" +
                "numero=" + numero +
                ", valorAmortizacao=" + valorAmortizacao +
                ", valorJuros=" + valorJuros +
                ", valorPrestacao=" + valorPrestacao +
                '}';
    }
}