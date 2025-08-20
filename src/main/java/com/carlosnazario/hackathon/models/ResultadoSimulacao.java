package com.carlosnazario.hackathon.models;

import com.carlosnazario.hackathon.converters.ListParcelaConverter;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Embeddable
public class ResultadoSimulacao {

    private Tipo tipo;

    @Convert(converter = ListParcelaConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Parcela> parcelas;

    // Construtor sem argumentos
    public ResultadoSimulacao() {
    }

    // Construtor com todos os argumentos
    public ResultadoSimulacao(Tipo tipo, List<Parcela> parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    // Getters e Setters
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultadoSimulacao that = (ResultadoSimulacao) o;
        return tipo == that.tipo && Objects.equals(parcelas, that.parcelas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, parcelas);
    }

    // Método toString
    @Override
    public String toString() {
        return "ResultadoSimulacao{" +
                "tipo=" + tipo +
                ", parcelas=" + parcelas +
                '}';
    }
}