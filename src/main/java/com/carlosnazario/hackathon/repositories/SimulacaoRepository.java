package com.carlosnazario.hackathon.repositories;

import com.carlosnazario.hackathon.models.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {
}