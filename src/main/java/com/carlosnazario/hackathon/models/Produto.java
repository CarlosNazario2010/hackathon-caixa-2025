package com.carlosnazario.hackathon.models;

import java.math.BigDecimal;

public enum Produto {
    PRODUTO_1(1, "Produto 1", new BigDecimal("0.0179")),
    PRODUTO_2(2, "Produto 2", new BigDecimal("0.0175")),
    PRODUTO_3(3, "Produto 3", new BigDecimal("0.182")),
    PRODUTO_4(4, "Produto 4", new BigDecimal("0.0151"));

    private final int codigo;
    private final String descricao;
    private final BigDecimal taxaJuros;

    Produto(int codigo, String descricao, BigDecimal taxaJuros) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.taxaJuros = taxaJuros;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }
}