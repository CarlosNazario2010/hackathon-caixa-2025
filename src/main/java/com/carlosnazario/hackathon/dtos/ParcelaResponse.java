// src/main/java/com/carlosnazario/hackathon/dtos/ParcelaResponse.java

package com.carlosnazario.hackathon.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public class ParcelaResponse {
    private int numero;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorPrestacao;

    // Construtor com todos os argumentos
    public ParcelaResponse(int numero, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorPrestacao) {
        this.numero = numero;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorPrestacao = valorPrestacao;
    }

    // Getters
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

    // Métodos equals e hashCode
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

    // Método toString
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