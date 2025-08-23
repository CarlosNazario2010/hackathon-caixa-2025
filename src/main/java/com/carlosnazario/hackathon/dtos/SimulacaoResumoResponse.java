package com.carlosnazario.hackathon.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class SimulacaoResumoResponse {
    private Long idSimulacao;
    private LocalDateTime dataSimulacao;
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaJuros;
    private BigDecimal valorDesejado;
    private Integer prazo;
    private BigDecimal valorTotalParcelasSAC;
    private BigDecimal valorTotalParcelasPRICE;

    public SimulacaoResumoResponse() {
    }

    public SimulacaoResumoResponse(Long idSimulacao, int codigoProduto, String descricaoProduto, BigDecimal taxaJuros, BigDecimal valorDesejado, Integer prazo, LocalDateTime dataSimulacao, BigDecimal valorTotalParcelasSAC, BigDecimal valorTotalParcelasPRICE) {
        this.idSimulacao = idSimulacao;
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaJuros = taxaJuros;
        this.valorDesejado = valorDesejado;
        this.prazo = prazo;
        this.dataSimulacao = dataSimulacao;
        this.valorTotalParcelasSAC = valorTotalParcelasSAC;
        this.valorTotalParcelasPRICE = valorTotalParcelasPRICE;
    }

    public Long getIdSimulacao() { return idSimulacao; }
    public void setIdSimulacao(Long idSimulacao) { this.idSimulacao = idSimulacao; }
    public int getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(int codigoProduto) { this.codigoProduto = codigoProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public BigDecimal getValorDesejado() { return valorDesejado; }
    public void setValorDesejado(BigDecimal valorDesejado) { this.valorDesejado = valorDesejado; }
    public Integer getPrazo() { return prazo; }
    public void setPrazo(Integer prazo) { this.prazo = prazo; }
    public BigDecimal getValorTotalParcelasSAC() { return valorTotalParcelasSAC; }
    public void setValorTotalParcelasSAC(BigDecimal valorTotalParcelasSAC) { this.valorTotalParcelasSAC = valorTotalParcelasSAC; }
    public BigDecimal getValorTotalParcelasPRICE() { return valorTotalParcelasPRICE; }
    public void setValorTotalParcelasPRICE(BigDecimal valorTotalParcelasPRICE) { this.valorTotalParcelasPRICE = valorTotalParcelasPRICE; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulacaoResumoResponse that = (SimulacaoResumoResponse) o;
        return codigoProduto == that.codigoProduto && Objects.equals(idSimulacao, that.idSimulacao) && Objects.equals(descricaoProduto, that.descricaoProduto) && Objects.equals(taxaJuros, that.taxaJuros) && Objects.equals(valorDesejado, that.valorDesejado) && Objects.equals(prazo, that.prazo) && Objects.equals(valorTotalParcelasSAC, that.valorTotalParcelasSAC) && Objects.equals(valorTotalParcelasPRICE, that.valorTotalParcelasPRICE);
    }
    @Override
    public int hashCode() { return Objects.hash(idSimulacao, codigoProduto, descricaoProduto, taxaJuros, valorDesejado, prazo, valorTotalParcelasSAC, valorTotalParcelasPRICE); }

    @Override
    public String toString() {
        return "SimulacaoResumoResponse{" +
                "idSimulacao=" + idSimulacao +
                ", codigoProduto=" + codigoProduto +
                ", descricaoProduto='" + descricaoProduto + '\'' +
                ", taxaJuros=" + taxaJuros +
                ", valorDesejado=" + valorDesejado +
                ", prazo=" + prazo +
                ", valorTotalParcelasSAC=" + valorTotalParcelasSAC +
                ", valorTotalParcelasPRICE=" + valorTotalParcelasPRICE +
                '}';
    }
}