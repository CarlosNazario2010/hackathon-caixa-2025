package com.carlosnazario.hackathon.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Simulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;

    private BigDecimal valorDesejado;
    private Integer prazo;

    @CreationTimestamp
    private LocalDateTime dataSimulacao;

    @Enumerated(EnumType.STRING)
    private Produto produto;

    @ElementCollection
    @CollectionTable(name = "simulacao_resultados", joinColumns = @JoinColumn(name = "simulacao_id"))
    private List<ResultadoSimulacao> resultados;

    public Simulacao() {
    }

    public Simulacao(Long idSimulacao, BigDecimal valorDesejado, Integer prazo, LocalDateTime dataSimulacao, Produto produto, List<ResultadoSimulacao> resultados) {
        this.idSimulacao = idSimulacao;
        this.valorDesejado = valorDesejado;
        this.prazo = prazo;
        this.dataSimulacao = dataSimulacao;
        this.produto = produto;
        this.resultados = resultados;
    }

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

    public BigDecimal getValorDesejado() { return valorDesejado; }

    public void setValorDesejado(BigDecimal valorDesejado) { this.valorDesejado = valorDesejado; }
    public Integer getPrazo() { return prazo; }

    public void setPrazo(Integer prazo) { this.prazo = prazo; }

    public LocalDateTime getDataSimulacao() { return dataSimulacao; } // NOVO GETTER
    public void setDataSimulacao(LocalDateTime dataSimulacao) { this.dataSimulacao = dataSimulacao; } // NOVO SETTER

    public List<ResultadoSimulacao> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoSimulacao> resultados) {
        this.resultados = resultados;
    }

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

    @Override
    public String toString() {
        return "Simulacao{" +
                "idSimulacao=" + idSimulacao +
                ", produto=" + produto +
                ", resultados=" + resultados +
                '}';
    }
}