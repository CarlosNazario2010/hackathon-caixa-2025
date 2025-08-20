package com.carlosnazario.hackathon.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;

    // Construtor sem argumentos (equivalente a @NoArgsConstructor)
    public Mensagem() {
    }

    // Construtor com todos os argumentos (equivalente a @AllArgsConstructor)
    public Mensagem(Long id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    // Construtor para resolver o problema (mantido do seu código original)
    public Mensagem(String texto) {
        this.texto = texto;
    }

    // Getters e Setters (equivalente a @Data)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // Método equals (equivalente a @Data)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return Objects.equals(id, mensagem.id) && Objects.equals(texto, mensagem.texto);
    }

    // Método hashCode (equivalente a @Data)
    @Override
    public int hashCode() {
        return Objects.hash(id, texto);
    }

    // Método toString (equivalente a @Data)
    @Override
    public String toString() {
        return "Mensagem{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                '}';
    }
}