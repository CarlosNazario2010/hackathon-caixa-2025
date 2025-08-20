package com.carlosnazario.hackathon.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;

    @Enumerated(EnumType.STRING)
    private Produto produto;

    @ElementCollection
    @CollectionTable(name = "simulacao_resultados", joinColumns = @JoinColumn(name = "simulacao_id"))
    private List<ResultadoSimulacao> resultados;

    // Construtor sem argumentos
    public Simulacao() {
    }

    // Construtor com todos os argumentos
    public Simulacao(Long idSimulacao, Produto produto, List<ResultadoSimulacao> resultados) {
        this.idSimulacao = idSimulacao;
        this.produto = produto;
        this.resultados = resultados;
    }

    // Getters e Setters
    public Long getIdSimulacao() {
        return idSimulacao;
    }

    public void setIdSimulacao(Long idSimulacao) {
        this.idSimulacao = idSimulacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<ResultadoSimulacao> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoSimulacao> resultados) {
        this.resultados = resultados;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simulacao simulacao = (Simulacao) o;
        return Objects.equals(idSimulacao, simulacao.idSimulacao) && produto == simulacao.produto && Objects.equals(resultados, simulacao.resultados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSimulacao, produto, resultados);
    }

    // Método toString
    @Override
    public String toString() {
        return "Simulacao{" +
                "idSimulacao=" + idSimulacao +
                ", produto=" + produto +
                ", resultados=" + resultados +
                '}';
    }
}