package com.carlosnazario.hackathon.dtos;

import com.carlosnazario.hackathon.models.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class SimulacaoResponse {
    private Long idSimulacao;
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaJuros;
    private List<ResultadoSimulacaoResponse> resultadoSimulacao;

    // Construtor sem argumentos
    public SimulacaoResponse() {
    }

    // Construtor com todos os argumentos
    public SimulacaoResponse(Long idSimulacao, Produto produto, List<ResultadoSimulacaoResponse> resultadoSimulacao) {
        this.idSimulacao = idSimulacao;
        this.codigoProduto = produto.getCodigo();
        this.descricaoProduto = produto.getDescricao();
        this.taxaJuros = produto.getTaxaJuros();
        this.resultadoSimulacao = resultadoSimulacao;
    }

    // Getters e Setters
    public Long getIdSimulacao() {
        return idSimulacao;
    }

    public void setIdSimulacao(Long idSimulacao) {
        this.idSimulacao = idSimulacao;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public List<ResultadoSimulacaoResponse> getResultadoSimulacao() {
        return resultadoSimulacao;
    }

    public void setResultadoSimulacao(List<ResultadoSimulacaoResponse> resultadoSimulacao) {
        this.resultadoSimulacao = resultadoSimulacao;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulacaoResponse that = (SimulacaoResponse) o;
        return codigoProduto == that.codigoProduto && Objects.equals(idSimulacao, that.idSimulacao) && Objects.equals(descricaoProduto, that.descricaoProduto) && Objects.equals(taxaJuros, that.taxaJuros) && Objects.equals(resultadoSimulacao, that.resultadoSimulacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSimulacao, codigoProduto, descricaoProduto, taxaJuros, resultadoSimulacao);
    }

    // Método toString
    @Override
    public String toString() {
        return "SimulacaoResponse{" +
                "idSimulacao=" + idSimulacao +
                ", codigoProduto=" + codigoProduto +
                ", descricaoProduto='" + descricaoProduto + '\'' +
                ", taxaJuros=" + taxaJuros +
                ", resultadoSimulacao=" + resultadoSimulacao +
                '}';
    }
}