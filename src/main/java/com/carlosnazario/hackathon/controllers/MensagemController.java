package com.carlosnazario.hackathon.controllers;

import com.carlosnazario.hackathon.models.Mensagem;
import com.carlosnazario.hackathon.services.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class MensagemController {

    private final MensagemService mensagemService;

    @Autowired
    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @GetMapping
    public Mensagem helloWorld() {
        return mensagemService.getMensagem();
    }
}