package com.carlosnazario.hackathon.controllers;

import com.carlosnazario.hackathon.dtos.*;
import com.carlosnazario.hackathon.models.Simulacao;
import com.carlosnazario.hackathon.models.ResultadoSimulacao;
import com.carlosnazario.hackathon.models.Parcela;
import com.carlosnazario.hackathon.services.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
        Simulacao simulacao = simulacaoService.realizarSimulacao(
                request.valorDesejado(),
                request.prazo()
        );
        SimulacaoResponse response = toSimulacaoResponse(simulacao);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<SimulacaoResumoResponse>> listarResumoDeSimulacoes() {
        List<SimulacaoResumoResponse> resumo = simulacaoService.listarResumoDeSimulacoes();
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacaoResponse> buscarPorId(@PathVariable Long id) {
        Optional<Simulacao> simulacao = simulacaoService.buscarSimulacaoPorId(id);
        return simulacao.map(this::toSimulacaoResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/agregada")
    public ResponseEntity<SimulacaoPorDataResponse> listarSimulacoesAgregadasPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        SimulacaoPorDataResponse response = simulacaoService.listarSimulacoesPorDataAgregadas(data);
        return ResponseEntity.ok(response);
    }


    //################################################################################



    // Métodos de conversão de Model para DTO

    private SimulacaoResponse toSimulacaoResponse(Simulacao simulacao) {
        List<ResultadoSimulacaoResponse> resultadosResponse = simulacao.getResultados().stream()
                .map(this::toResultadoSimulacaoResponse)
                .collect(Collectors.toList());

        return new SimulacaoResponse(
                simulacao.getIdSimulacao(),
                simulacao.getProduto(),
                simulacao.getValorDesejado(),
                simulacao.getPrazo(),
                simulacao.getDataSimulacao(),
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