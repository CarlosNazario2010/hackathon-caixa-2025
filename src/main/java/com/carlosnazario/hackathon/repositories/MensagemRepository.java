package com.carlosnazario.hackathon.repositories;

import com.carlosnazario.hackathon.models.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}
