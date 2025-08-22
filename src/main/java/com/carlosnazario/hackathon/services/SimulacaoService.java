package com.carlosnazario.hackathon.services;
import com.carlosnazario.hackathon.dtos.SimulacaoResumoResponse;
import com.carlosnazario.hackathon.models.Parcela;
import com.carlosnazario.hackathon.models.Produto;
import com.carlosnazario.hackathon.models.ResultadoSimulacao;
import com.carlosnazario.hackathon.models.Simulacao;
import com.carlosnazario.hackathon.models.Tipo;
import com.carlosnazario.hackathon.repositories.SimulacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;

    @Autowired
    public SimulacaoService(SimulacaoRepository simulacaoRepository) {
        this.simulacaoRepository = simulacaoRepository;
    }

    public Simulacao realizarSimulacao(BigDecimal valorSolicitado, int numeroParcelas) {
        // 1. Determina o produto com base nas regras de negócio
        Produto produto = determinarProduto(valorSolicitado, numeroParcelas);
        BigDecimal taxaJuros = produto.getTaxaJuros();

        // 2. Calcula os resultados para SAC e PRICE
        List<ResultadoSimulacao> resultados = new ArrayList<>();
        resultados.add(calcularSac(valorSolicitado, numeroParcelas, taxaJuros));
        resultados.add(calcularPrice(valorSolicitado, numeroParcelas, taxaJuros));

        // 3. Cria a entidade Simulacao para persistência
        Simulacao simulacao = new Simulacao();
        simulacao.setValorDesejado(valorSolicitado); // Preenche o novo campo
        simulacao.setPrazo(numeroParcelas); // Preenche o novo campo
        simulacao.setProduto(produto);
        simulacao.setResultados(resultados);

        // 4. Salva a simulação no banco de dados e a retorna
        return simulacaoRepository.save(simulacao);
    }

    private Produto determinarProduto(BigDecimal valor, int prazo) {

        // Regra para PRODUTO_4: Valor acima de R$ 1.000.000 OU Prazo maior que 96 meses
        if (valor.compareTo(new BigDecimal("1000000")) > 0 || prazo > 96) {
            return Produto.PRODUTO_4;
        }

        // Regra para PRODUTO_3: Valor entre R$ 100.000,01 e R$ 1.000.000 OU Prazo entre 49 e 96 meses
        else if ((valor.compareTo(new BigDecimal("100000.01")) >= 0 && valor.compareTo(new BigDecimal("1000000")) <= 0) || (prazo >= 49 && prazo <= 96)) {
            return Produto.PRODUTO_3;
        }

        // Regra para PRODUTO_2: Valor entre R$ 10.000,01 e R$ 100.000 OU Prazo entre 25 e 48 meses
        else if ((valor.compareTo(new BigDecimal("10000.01")) >= 0 && valor.compareTo(new BigDecimal("100000")) <= 0) || (prazo >= 25 && prazo <= 48)) {
            return Produto.PRODUTO_2;
        }

        // Regra para PRODUTO_1: Valor entre R$ 200 e R$ 10.000 OU Prazo entre 0 e 24 meses
        else if ((valor.compareTo(new BigDecimal("200")) >= 0 && valor.compareTo(new BigDecimal("10000")) <= 0) || (prazo >= 0 && prazo <= 24)) {
            return Produto.PRODUTO_1;
        }

        // Retorna um valor padrão ou lança uma exceção se nenhuma regra se aplicar
        return Produto.PRODUTO_1;
    }

    private ResultadoSimulacao calcularSac(BigDecimal valorInicial, int numParcelas, BigDecimal taxaJuros) {
        BigDecimal saldoDevedor = valorInicial;
        BigDecimal amortizacaoConstante = valorInicial.divide(new BigDecimal(numParcelas), 2, RoundingMode.HALF_UP);
        List<Parcela> parcelas = new ArrayList<>();

        for (int i = 1; i <= numParcelas; i++) {
            BigDecimal juros = saldoDevedor.multiply(taxaJuros).setScale(2, RoundingMode.HALF_UP);
            BigDecimal prestacao = amortizacaoConstante.add(juros).setScale(2, RoundingMode.HALF_UP);

            saldoDevedor = saldoDevedor.subtract(amortizacaoConstante);

            Parcela parcela = new Parcela(i, amortizacaoConstante, juros, prestacao);
            parcelas.add(parcela);
        }

        return new ResultadoSimulacao(Tipo.SAC, parcelas);
    }

    private ResultadoSimulacao calcularPrice(BigDecimal valorInicial, int numParcelas, BigDecimal taxaJuros) {
        BigDecimal jurosMensal = taxaJuros;
        BigDecimal saldoDevedor = valorInicial;

        // Fórmula para a prestação constante: PMT = PV * [i * (1+i)^n] / [(1+i)^n - 1]
        BigDecimal fator1 = jurosMensal.add(BigDecimal.ONE).pow(numParcelas);
        BigDecimal fator2 = jurosMensal.multiply(fator1);
        BigDecimal fator3 = fator1.subtract(BigDecimal.ONE);
        BigDecimal prestacaoConstante = valorInicial.multiply(fator2.divide(fator3, 4, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
        List<Parcela> parcelas = new ArrayList<>();

        for (int i = 1; i <= numParcelas; i++) {
            BigDecimal juros = saldoDevedor.multiply(jurosMensal).setScale(2, RoundingMode.HALF_UP);
            BigDecimal amortizacao = prestacaoConstante.subtract(juros).setScale(2, RoundingMode.HALF_UP);

            saldoDevedor = saldoDevedor.subtract(amortizacao);

            Parcela parcela = new Parcela(i, amortizacao, juros, prestacaoConstante);
            parcelas.add(parcela);
        }

        return new ResultadoSimulacao(Tipo.PRICE, parcelas);
    }


    //##############################################################################################################

    public Optional<Simulacao> buscarSimulacaoPorId(Long id) {
        return simulacaoRepository.findById(id);
    }

    //Retorna todas as simulações em formato de resumo
    public List<SimulacaoResumoResponse> listarResumoDeSimulacoes() {
        List<Simulacao> simulacoes = simulacaoRepository.findAll();

        return simulacoes.stream()
                .map(this::converterParaSimulacaoResumoResponse)
                .collect(Collectors.toList());
    }

    // Método auxiliar para converter Simulacao em SimulacaoResumoResponse
    private SimulacaoResumoResponse converterParaSimulacaoResumoResponse(Simulacao simulacao) {
        BigDecimal valorTotalSAC = BigDecimal.ZERO;
        BigDecimal valorTotalPRICE = BigDecimal.ZERO;

        for (ResultadoSimulacao resultado : simulacao.getResultados()) {
            if (resultado.getTipo() == Tipo.SAC) {
                valorTotalSAC = resultado.getParcelas().stream()
                        .map(Parcela::getValorPrestacao)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            } else if (resultado.getTipo() == Tipo.PRICE) {
                valorTotalPRICE = resultado.getParcelas().stream()
                        .map(Parcela::getValorPrestacao)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

        return new SimulacaoResumoResponse(
                simulacao.getIdSimulacao(),
                simulacao.getProduto().getCodigo(),
                simulacao.getProduto().getDescricao(),
                simulacao.getProduto().getTaxaJuros(),
                simulacao.getValorDesejado(),
                simulacao.getPrazo(),
                valorTotalSAC,
                valorTotalPRICE
        );
    }

}