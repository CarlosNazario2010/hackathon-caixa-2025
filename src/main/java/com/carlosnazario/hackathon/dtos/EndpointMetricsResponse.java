// src/main/java/com/carlosnazario/hackathon/dtos/EndpointMetricsResponse.java

package com.carlosnazario.hackathon.dtos;

import java.time.LocalDateTime;

public class EndpointMetricsResponse {

    private LocalDateTime dataAcesso;
    private String endpointAcessado;
    private long quantidadeRequisicoes;
    private double tempoMedioMs;
    private double tempoMinimoMs;
    private double tempoMaximoMs;
    private double percentualSucesso;

    public EndpointMetricsResponse() {
    }

    public EndpointMetricsResponse(LocalDateTime dataAcesso, String endpointAcessado, long quantidadeRequisicoes, double tempoMedioMs, double tempoMinimoMs, double tempoMaximoMs, double percentualSucesso) {
        this.dataAcesso = dataAcesso;
        this.endpointAcessado = endpointAcessado;
        this.quantidadeRequisicoes = quantidadeRequisicoes;
        this.tempoMedioMs = tempoMedioMs;
        this.tempoMinimoMs = tempoMinimoMs;
        this.tempoMaximoMs = tempoMaximoMs;
        this.percentualSucesso = percentualSucesso;
    }

    public LocalDateTime getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(LocalDateTime dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    public String getEndpointAcessado() {
        return endpointAcessado;
    }

    public void setEndpointAcessado(String endpointAcessado) {
        this.endpointAcessado = endpointAcessado;
    }

    public long getQuantidadeRequisicoes() {
        return quantidadeRequisicoes;
    }

    public void setQuantidadeRequisicoes(long quantidadeRequisicoes) {
        this.quantidadeRequisicoes = quantidadeRequisicoes;
    }

    public double getTempoMedioMs() {
        return tempoMedioMs;
    }

    public void setTempoMedioMs(double tempoMedioMs) {
        this.tempoMedioMs = tempoMedioMs;
    }

    public double getTempoMinimoMs() {
        return tempoMinimoMs;
    }

    public void setTempoMinimoMs(double tempoMinimoMs) {
        this.tempoMinimoMs = tempoMinimoMs;
    }

    public double getTempoMaximoMs() {
        return tempoMaximoMs;
    }

    public void setTempoMaximoMs(double tempoMaximoMs) {
        this.tempoMaximoMs = tempoMaximoMs;
    }

    public double getPercentualSucesso() {
        return percentualSucesso;
    }

    public void setPercentualSucesso(double percentualSucesso) {
        this.percentualSucesso = percentualSucesso;
    }
}