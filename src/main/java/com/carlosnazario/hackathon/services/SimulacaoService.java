package com.carlosnazario.hackathon.services;
import com.carlosnazario.hackathon.dtos.ProdutoAgregadoResponse;
import com.carlosnazario.hackathon.dtos.SimulacaoPorDataResponse;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    //##############################################################################################################


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
                simulacao.getDataSimulacao(),
                valorTotalSAC,
                valorTotalPRICE
        );
    }


    //##########################################################################################


    public SimulacaoPorDataResponse listarSimulacoesPorDataAgregadas(LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.atTime(LocalTime.MAX);

        List<Simulacao> simulacoes = simulacaoRepository.findByDataSimulacaoBetween(dataInicio, dataFim);

        if (simulacoes.isEmpty()) {
            return new SimulacaoPorDataResponse(List.of(), List.of());
        }

        // 1. Cria uma única lista de SimulacaoWrapper com todos os resultados (SAC e PRICE)
        List<SimulacaoWrapper> simulacoesWrapper = simulacoes.stream()
                .flatMap(simulacao -> simulacao.getResultados().stream()
                        .map(resultado -> new SimulacaoWrapper(simulacao, resultado.getTipo())))
                .collect(Collectors.toList());

        // 2. Agrupa por tipo (SAC/PRICE) a partir da lista de wrappers
        Map<Tipo, List<SimulacaoWrapper>> simulacoesPorTipo = simulacoesWrapper.stream()
                .collect(Collectors.groupingBy(SimulacaoWrapper::getTipo));

        // 3. Processa cada grupo (SAC e PRICE) separadamente
        List<ProdutoAgregadoResponse> listaSac = processarAgregacoes(simulacoesPorTipo.getOrDefault(Tipo.SAC, List.of()));
        List<ProdutoAgregadoResponse> listaPrice = processarAgregacoes(simulacoesPorTipo.getOrDefault(Tipo.PRICE, List.of()));

        return new SimulacaoPorDataResponse(listaSac, listaPrice);
    }

    // O método processarAgregacoes permanece o mesmo
    // NOVO MÉTODO AUXILIAR: Processa as agregações para um tipo (SAC ou PRICE)
    private List<ProdutoAgregadoResponse> processarAgregacoes(List<SimulacaoWrapper> simulacoesWrapper) {
        // Agrupa por produto
        Map<Produto, List<SimulacaoWrapper>> simulacoesPorProduto = simulacoesWrapper.stream()
                .collect(Collectors.groupingBy(wrapper -> wrapper.getSimulacao().getProduto()));

        // Mapeia para os DTOs de resposta agregados
        return simulacoesPorProduto.entrySet().stream()
                .map(entry -> {
                    Produto produto = entry.getKey();
                    List<SimulacaoWrapper> wrappers = entry.getValue();

                    // Calcula os valores agregados
                    double taxaMediaJuros = produto.getTaxaJuros().doubleValue();

                    // 1. Calcula a soma total de todas as prestações
                    BigDecimal valorTotalPrestacoes = wrappers.stream()
                            .map(w -> w.getSimulacao().getResultados().stream()
                                    .filter(r -> r.getTipo() == w.getTipo())
                                    .flatMap(r -> r.getParcelas().stream())
                                    .map(Parcela::getValorPrestacao)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // 2. Calcula a soma total de todos os valores solicitados
                    BigDecimal valorTotalSolicitado = wrappers.stream()
                            .map(w -> w.getSimulacao().getValorDesejado())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // 3. NOVO: Calcula o número total de parcelas somando o prazo de cada simulação
                    long totalDeParcelas = wrappers.stream()
                            .mapToLong(w -> w.getSimulacao().getPrazo())
                            .sum();

                    // 4. Calcula o valor médio da prestação com base no número total de parcelas
                    BigDecimal valorMedioPrestacao = BigDecimal.ZERO;
                    if (totalDeParcelas > 0) { // Evita divisão por zero
                        valorMedioPrestacao = valorTotalPrestacoes.divide(new BigDecimal(totalDeParcelas), 2, RoundingMode.HALF_UP);
                    }

                    return new ProdutoAgregadoResponse(
                            produto.getCodigo(),
                            produto.getDescricao(),
                            BigDecimal.valueOf(taxaMediaJuros),
                            valorMedioPrestacao,
                            valorTotalSolicitado,
                            valorTotalPrestacoes
                    );
                })
                .collect(Collectors.toList());
    }

    // NOVO: Classe auxiliar para agrupar simulação e tipo de resultado
    private static class SimulacaoWrapper {
        private final Simulacao simulacao;
        private final Tipo tipo;

        public SimulacaoWrapper(Simulacao simulacao, Tipo tipo) {
            this.simulacao = simulacao;
            this.tipo = tipo;
        }

        public Simulacao getSimulacao() { return simulacao; }
        public Tipo getTipo() { return tipo; }
    }

}