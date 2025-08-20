// src/main/java/com/carlosnazario/hackathon/controllers/SimulacaoController.java

package com.carlosnazario.hackathon.controllers;

import com.carlosnazario.hackathon.dtos.SimulacaoRequest;
import com.carlosnazario.hackathon.dtos.SimulacaoResponse;
import com.carlosnazario.hackathon.dtos.ResultadoSimulacaoResponse;
import com.carlosnazario.hackathon.dtos.ParcelaResponse;
import com.carlosnazario.hackathon.models.Simulacao;
import com.carlosnazario.hackathon.models.ResultadoSimulacao;
import com.carlosnazario.hackathon.models.Parcela;
import com.carlosnazario.hackathon.services.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simulacao")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    @Autowired
    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @PostMapping
    public ResponseEntity<SimulacaoResponse> realizarSimulacao(
            @RequestBody @Valid SimulacaoRequest request
    ) {
        // Chama o serviço com os dados da requisição
        Simulacao simulacao = simulacaoService.realizarSimulacao(
                request.valorDesejado(),
                request.prazo()
        );

        // Converte o objeto de domínio (Simulacao) para o DTO de resposta
        SimulacaoResponse response = toSimulacaoResponse(simulacao);

        return ResponseEntity.ok(response);
    }

    // Método de conversão de Model para DTO
    private SimulacaoResponse toSimulacaoResponse(Simulacao simulacao) {
        List<ResultadoSimulacaoResponse> resultadosResponse = simulacao.getResultados().stream()
                .map(this::toResultadoSimulacaoResponse)
                .collect(Collectors.toList());

        return new SimulacaoResponse(
                simulacao.getIdSimulacao(),
                simulacao.getProduto(),
                resultadosResponse
        );
    }

    private ResultadoSimulacaoResponse toResultadoSimulacaoResponse(ResultadoSimulacao resultado) {
        List<ParcelaResponse> parcelasResponse = resultado.getParcelas().stream()
                .map(this::toParcelaResponse)
                .collect(Collectors.toList());

        return new ResultadoSimulacaoResponse(
                resultado.getTipo(),
                parcelasResponse
        );
    }

    private ParcelaResponse toParcelaResponse(Parcela parcela) {
        return new ParcelaResponse(
                parcela.getNumero(),
                parcela.getValorAmortizacao(),
                parcela.getValorJuros(),
                parcela.getValorPrestacao()
        );
    }
}