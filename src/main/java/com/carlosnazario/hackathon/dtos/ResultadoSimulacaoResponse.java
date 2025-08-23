package com.carlosnazario.hackathon.dtos;

import com.carlosnazario.hackathon.models.Tipo;
import java.util.List;
import java.util.Objects;

public class ResultadoSimulacaoResponse {
    private Tipo tipo;
    private List<ParcelaResponse> parcelas;

    public ResultadoSimulacaoResponse() {
    }

    public ResultadoSimulacaoResponse(Tipo tipo, List<ParcelaResponse> parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public List<ParcelaResponse> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelaResponse> parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultadoSimulacaoResponse that = (ResultadoSimulacaoResponse) o;
        return tipo == that.tipo && Objects.equals(parcelas, that.parcelas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, parcelas);
    }

    @Override
    public String toString() {
        return "ResultadoSimulacaoResponse{" +
                "tipo=" + tipo +
                ", parcelas=" + parcelas +
                '}';
    }
}