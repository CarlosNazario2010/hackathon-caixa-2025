package com.carlosnazario.hackathon.services;


import com.carlosnazario.hackathon.models.Mensagem;
import com.carlosnazario.hackathon.repositories.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    @Autowired
    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public Mensagem getMensagem() {
        // Agora você pode usar o novo construtor
        Mensagem mensagem = new Mensagem("Olá, Mundo do Spring Boot!");
        System.out.println(mensagem);
        return mensagemRepository.save(mensagem);
    }
}
