package com.carlosnazario.hackathon.services;

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
        simulacao.setProduto(produto);
        simulacao.setResultados(resultados);

        // 4. Salva a simulação no banco de dados e a retorna
        return simulacaoRepository.save(simulacao);
    }

    private Produto determinarProduto(BigDecimal valor, int parcelas) {
        if ((valor.compareTo(new BigDecimal("200")) >= 0 && valor.compareTo(new BigDecimal("10000")) <= 0) || (parcelas >= 0 && parcelas <= 24)) {
            return Produto.PRODUTO_1;
        } else if ((parcelas >= 25 && parcelas <= 48) || (valor.compareTo(new BigDecimal("10000.01")) >= 0 && valor.compareTo(new BigDecimal("100000")) <= 0)) {
            return Produto.PRODUTO_2;
        } else if ((parcelas >= 49 && parcelas <= 96) || (valor.compareTo(new BigDecimal("100000.01")) >= 0 && valor.compareTo(new BigDecimal("1000000")) <= 0)) {
            return Produto.PRODUTO_3;
        } else if (valor.compareTo(new BigDecimal("1000000")) > 0 || parcelas > 96) {
            return Produto.PRODUTO_4;
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
}