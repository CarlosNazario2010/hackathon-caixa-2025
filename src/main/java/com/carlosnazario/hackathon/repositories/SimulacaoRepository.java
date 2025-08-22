package com.carlosnazario.hackathon.repositories;

import com.carlosnazario.hackathon.models.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {

    //Busca simulações onde a data está entre o período de tempo especificado.
    List<Simulacao> findByDataSimulacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}