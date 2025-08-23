package com.carlosnazario.hackathon.dtos;

import java.math.BigDecimal;

public class ProdutoAgregadoResponse {
    private int codigo;
    private String descricao;
    private BigDecimal taxaMediaDeJuros;
    private BigDecimal valorMedioDasPrestacoes;
    private BigDecimal valorTotalDosValoresSolicitados;
    private BigDecimal somaDeTodasAsPrestacoes;

    public ProdutoAgregadoResponse() {
    }

    public ProdutoAgregadoResponse(int codigo, String descricao, BigDecimal taxaMediaDeJuros, BigDecimal valorMedioDasPrestacoes, BigDecimal valorTotalDosValoresSolicitados, BigDecimal somaDeTodasAsPrestacoes) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.taxaMediaDeJuros = taxaMediaDeJuros;
        this.valorMedioDasPrestacoes = valorMedioDasPrestacoes;
        this.valorTotalDosValoresSolicitados = valorTotalDosValoresSolicitados;
        this.somaDeTodasAsPrestacoes = somaDeTodasAsPrestacoes;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getTaxaMediaDeJuros() {
        return taxaMediaDeJuros;
    }

    public void setTaxaMediaDeJuros(BigDecimal taxaMediaDeJuros) {
        this.taxaMediaDeJuros = taxaMediaDeJuros;
    }

    public BigDecimal getValorMedioDasPrestacoes() {
        return valorMedioDasPrestacoes;
    }

    public void setValorMedioDasPrestacoes(BigDecimal valorMedioDasPrestacoes) {
        this.valorMedioDasPrestacoes = valorMedioDasPrestacoes;
    }

    public BigDecimal getValorTotalDosValoresSolicitados() {
        return valorTotalDosValoresSolicitados;
    }

    public void setValorTotalDosValoresSolicitados(BigDecimal valorTotalDosValoresSolicitados) {
        this.valorTotalDosValoresSolicitados = valorTotalDosValoresSolicitados;
    }

    public BigDecimal getSomaDeTodasAsPrestacoes() {
        return somaDeTodasAsPrestacoes;
    }

    public void setSomaDeTodasAsPrestacoes(BigDecimal somaDeTodasAsPrestacoes) {
        this.somaDeTodasAsPrestacoes = somaDeTodasAsPrestacoes;
    }
}
