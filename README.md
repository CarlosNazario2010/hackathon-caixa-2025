# Sistema de Simulação de Empréstimo

Este projeto é uma API REST em Spring Boot para simular empréstimos com base em diferentes sistemas de amortização: **SAC** (Sistema de Amortização Constante) e **PRICE** (Sistema Francês de Amortização).

O sistema determina automaticamente o produto de empréstimo e a taxa de juros com base nos valores e prazos solicitados.

Este projeto foi desenvolvido como parte de um **Hackathon realizado pela Caixa em 2025**.

---

### 🚀 Como Rodar o Projeto

1.  **Pré-requisitos:**
    * Java 17 ou superior
    * Maven
    * Um ambiente de desenvolvimento como o IntelliJ IDEA ou VS Code

2.  **Clonar o Repositório:**
    ```bash
    git clone https://github.com/CarlosNazario2010/hackathon-caixa-2025
    cd hackathon
    ```

3.  **Executar a Aplicação:**
    Abra o projeto na sua IDE e execute a classe principal da aplicação Spring Boot. Alternativamente, você pode usar o Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

A aplicação será iniciada no endereço `http://localhost:8080`.

---

### 🛠️ Endpoints da API

#### `POST /api/simulacao`

Realiza uma simulação de empréstimo com base no valor desejado e no prazo.

-   **Regras de Negócio:** O produto e a taxa de juros são determinados com base nas regras a seguir, considerando o **valor solicitado** e o **prazo em meses**.

    * **PRODUTO_1**
        * **Condição:** Valor entre R$ 200 e R$ 10.000 **OU** Prazo entre 0 e 24 meses.
        * **Taxa de Juros:** 0.0179 (1.79% ao mês)

    * **PRODUTO_2**
        * **Condição:** Valor entre R$ 10.000,01 e R$ 100.000 **OU** Prazo entre 25 e 48 meses.
        * **Taxa de Juros:** 0.0175 (1.75% ao mês)

    * **PRODUTO_3**
        * **Condição:** Valor entre R$ 100.000,01 e R$ 1.000.000 **OU** Prazo entre 49 e 96 meses.
        * **Taxa de Juros:** 0.0182 (1.82% ao mês)

    * **PRODUTO_4**
        * **Condição:** Valor acima de R$ 1.000.000 **OU** Prazo maior que 96 meses.
        * **Taxa de Juros:** 0.0151 (1.51% ao mês)

-   **Parâmetros da Requisição (JSON Body):**
    ```json
    {
      "valorDesejado": 900.00,
      "prazo": 5
    }
    ```

-   **Resposta de Sucesso (200 OK):**
    Retorna um objeto `JSON` com os detalhes da simulação, incluindo o ID, o produto selecionado e os resultados de cálculo para os sistemas SAC e PRICE.

    ```json
    {
      "idSimulacao": 1,
      "codigoProduto": 1,
      "descricaoProduto": "Produto 1",
      "taxaJuros": 0.0179,
      "resultadoSimulacao": [
        {
          "tipo": "SAC",
          "parcelas": [
            {
              "numero": 1,
              "valorAmortizacao": 180.00,
              "valorJuros": 16.11,
              "valorPrestacao": 196.11
            },
            {
              "numero": 2,
              "valorAmortizacao": 180.00,
              "valorJuros": 12.89,
              "valorPrestacao": 192.89
            },
            {
              "numero": 3,
              "valorAmortizacao": 180.00,
              "valorJuros": 9.67,
              "valorPrestacao": 189.67
            },
            {
              "numero": 4,
              "valorAmortizacao": 180.00,
              "valorJuros": 6.44,
              "valorPrestacao": 186.44
            },
            {
              "numero": 5,
              "valorAmortizacao": 180.00,
              "valorJuros": 3.22,
              "valorPrestacao": 183.22
            }
          ]
        },
        {
          "tipo": "PRICE",
          "parcelas": [
            {
              "numero": 1,
              "valorAmortizacao": 172.89,
              "valorJuros": 16.11,
              "valorPrestacao": 189.00
            },
            {
              "numero": 2,
              "valorAmortizacao": 175.98,
              "valorJuros": 13.02,
              "valorPrestacao": 189.00
            },
            {
              "numero": 3,
              "valorAmortizacao": 179.13,
              "valorJuros": 9.87,
              "valorPrestacao": 189.00
            },
            {
              "numero": 4,
              "valorAmortizacao": 182.34,
              "valorJuros": 6.66,
              "valorPrestacao": 189.00
            },
            {
              "numero": 5,
              "valorAmortizacao": 185.61,
              "valorJuros": 3.39,
              "valorPrestacao": 189.00
            }
          ]
        }
      ]
    }
    ```



---
### 🛠️ Endpoints da API

#### `GET /api/simulacao/resumo`

Retorna uma lista resumida de todas as simulações cadastradas, com os principais dados de cada uma.

-   **Parâmetros da Requisição:** Este endpoint não possui parâmetros.

-   **Resposta de Sucesso (200 OK):**
    Retorna uma lista de objetos `JSON` com um resumo de cada simulação, incluindo os valores totais de prestações calculados para os sistemas SAC e PRICE.

    ```json
    [
        {
            "idSimulacao": 1,
            "codigoProduto": 1,
            "descricaoProduto": "Produto 1",
            "taxaJuros": 0.0179,
            "valorDesejado": 500.00,
            "prazo": 5,
            "valorTotalParcelasSAC": 526.85,
            "valorTotalParcelasPRICE": 527.25
        },
        {
            "idSimulacao": 2,
            "codigoProduto": 2,
            "descricaoProduto": "Produto 2",
            "taxaJuros": 0.0175,
            "valorDesejado": 15000.00,
            "prazo": 25,
            "valorTotalParcelasSAC": 18412.50,
            "valorTotalParcelasPRICE": 18637.50
        },
        {
            "idSimulacao": 3,
            "codigoProduto": 3,
            "descricaoProduto": "Produto 3",
            "taxaJuros": 0.0182,
            "valorDesejado": 120000.00,
            "prazo": 60,
            "valorTotalParcelasSAC": 186612.00,
            "valorTotalParcelasPRICE": 198000.00
        },
        {
            "idSimulacao": 4,
            "codigoProduto": 4,
            "descricaoProduto": "Produto 4",
            "taxaJuros": 0.0151,
            "valorDesejado": 1200000.00,
            "prazo": 120,
            "valorTotalParcelasSAC": 2296260.00,
            "valorTotalParcelasPRICE": 2606400.00
        },
        {
            "idSimulacao": 5,
            "codigoProduto": 4,
            "descricaoProduto": "Produto 4",
            "taxaJuros": 0.0151,
            "valorDesejado": 1200000.00,
            "prazo": 120,
            "valorTotalParcelasSAC": 2296260.00,
            "valorTotalParcelasPRICE": 2606400.00
        }
    ]
    ```

---
### 🛠️ Endpoints da API

#### `GET /api/simulacao/{id}`

Busca os detalhes de uma simulação específica pelo seu identificador único.

-   **Parâmetros de Caminho (`Path`):**
    * `id` (inteiro): O identificador único da simulação que se deseja buscar.

-   **Resposta de Sucesso (200 OK):**
    Retorna um objeto `JSON` com todos os detalhes da simulação encontrada, incluindo o produto, o valor desejado e os cálculos completos para os sistemas SAC e PRICE.

    ```json
    {
        "idSimulacao": 1,
        "codigoProduto": 1,
        "descricaoProduto": "Produto 1",
        "taxaJuros": 0.0179,
        "valorDesejado": 1000.00,
        "prazo": 10,
        "resultadoSimulacao": [
            {
                "tipo": "SAC",
                "parcelas": [
                    {
                        "numero": 1,
                        "valorAmortizacao": 100.00,
                        "valorJuros": 17.90,
                        "valorPrestacao": 117.90
                    },
                    {
                        "numero": 2,
                        "valorAmortizacao": 100.00,
                        "valorJuros": 16.11,
                        "valorPrestacao": 116.11
                    },
                    {
                        "numero": 3,
                        "valorAmortizacao": 100.00,
                        "valorJuros": 14.32,
                        "valorPrestacao": 114.32
                    },
                    {
                        "numero": 4,
                        "valorAmortizacao": 100.00,
                        "valorJuros": 12.53,
                        "valorPrestacao": 112.53
                    },
                    {
                        "numero": 5,
                        "valorAmortizacao": 100.00,
                        "valorJuros": 10.74,
                        "valorPrestacao": 110.74
                    },
                    {
                        "numero": 6,
                        "valorAmortizacao": 100.00,
                        "valorJuros": 8.95,
                        "valorPrestacao": 108.95
                    },
                    {
                        "numero": 7,
                        "valorAmortizacao": 102.56,
                        "valorJuros": 7.54,
                        "valorPrestacao": 110.10
                    },
                    {
                        "numero": 8,
                        "valorAmortizacao": 104.39,
                        "valorJuros": 5.71,
                        "valorPrestacao": 110.10
                    },
                    {
                        "numero": 9,
                        "valorAmortizacao": 106.26,
                        "valorJuros": 3.84,
                        "valorPrestacao": 110.10
                    },
                    {
                        "numero": 10,
                        "valorAmortizacao": 108.16,
                        "valorJuros": 1.94,
                        "valorPrestacao": 110.10
                    }
                ]
            },
            {
            "tipo": "PRICE",
            "parcelas": [
                {
                    "numero": 1,
                    "valorAmortizacao": 92.20,
                    "valorJuros": 17.90,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 2,
                    "valorAmortizacao": 93.85,
                    "valorJuros": 16.25,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 3,
                    "valorAmortizacao": 95.53,
                    "valorJuros": 14.57,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 4,
                    "valorAmortizacao": 97.24,
                    "valorJuros": 12.86,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 5,
                    "valorAmortizacao": 98.98,
                    "valorJuros": 11.12,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 6,
                    "valorAmortizacao": 100.75,
                    "valorJuros": 9.35,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 7,
                    "valorAmortizacao": 102.56,
                    "valorJuros": 7.54,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 8,
                    "valorAmortizacao": 104.39,
                    "valorJuros": 5.71,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 9,
                    "valorAmortizacao": 106.26,
                    "valorJuros": 3.84,
                    "valorPrestacao": 110.10
                },
                {
                    "numero": 10,
                    "valorAmortizacao": 108.16,
                    "valorJuros": 1.94,
                    "valorPrestacao": 110.10
                }
            ]
        }
    ]
}

---
### 🛠️ Endpoints da API

#### `GET /api/simulacao/agregada?data={data}`

Retorna um resumo agregado das simulações de um dia específico, com dados agrupados por tipo (SAC e PRICE) e por produto.

-   **Parâmetros da Requisição (`Query Parameter`):**
    * `data` (string no formato `AAAA-MM-DD`): A data das simulações que se deseja agregar.

-   **Exemplo de Chamada:**
    `http://localhost:8080/api/simulacao/agregada?data=2025-08-22`

-   **Resposta de Sucesso (200 OK):**
    Retorna um objeto `JSON` contendo duas listas, `listaSac` e `listaPrice`. Cada lista contém dados agregados para cada produto simulado no dia, com informações como valor médio das prestações e o total solicitado.

    ```json
    {
        "listaSac": [
            {
                "codigo": 4,
                "descricao": "Produto 4",
                "taxaMediaDeJuros": 0.0151,
                "valorMedioDasPrestacoes": 19135.50,
                "valorTotalDosValoresSolicitados": 1200000.00,
                "somaDeTodasAsPrestacoes": 2296260.00
            },
            {
                "codigo": 1,
                "descricao": "Produto 1",
                "taxaMediaDeJuros": 0.0179,
                "valorMedioDasPrestacoes": 109.85,
                "valorTotalDosValoresSolicitados": 1000.00,
                "somaDeTodasAsPrestacoes": 1098.45
            },
            {
                "codigo": 2,
                "descricao": "Produto 2",
                "taxaMediaDeJuros": 0.0175,
                "valorMedioDasPrestacoes": 635.63,
                "valorTotalDosValoresSolicitados": 15000.00,
                "somaDeTodasAsPrestacoes": 19068.75
            },
            {
                "codigo": 3,
                "descricao": "Produto 3",
                "taxaMediaDeJuros": 0.0182,
                "valorMedioDasPrestacoes": 3474.20,
                "valorTotalDosValoresSolicitados": 160000.00,
                "somaDeTodasAsPrestacoes": 277936.00
            }
        ],
        "listaPrice": [
            {
                "codigo": 4,
                "descricao": "Produto 4",
                "taxaMediaDeJuros": 0.0151,
                "valorMedioDasPrestacoes": 21720.00,
                "valorTotalDosValoresSolicitados": 1200000.00,
                "somaDeTodasAsPrestacoes": 2606400.00
            },
            {
                "codigo": 1,
                "descricao": "Produto 1",
                "taxaMediaDeJuros": 0.0179,
                "valorMedioDasPrestacoes": 110.10,
                "valorTotalDosValoresSolicitados": 1000.00,
                "somaDeTodasAsPrestacoes": 1101.00
            },
            {
                "codigo": 2,
                "descricao": "Produto 2",
                "taxaMediaDeJuros": 0.0175,
                "valorMedioDasPrestacoes": 646.50,
                "valorTotalDosValoresSolicitados": 15000.00,
                "somaDeTodasAsPrestacoes": 19395.00
            },
            {
                "codigo": 3,
                "descricao": "Produto 3",
                "taxaMediaDeJuros": 0.0182,
                "valorMedioDasPrestacoes": 3808.00,
                "valorTotalDosValoresSolicitados": 160000.00,
                "somaDeTodasAsPrestacoes": 304640.00
            }
        ]
    }
    ```

### 📋 Informações Adicionais

1.  O projeto foi criado em Spring Boot 3.3.2.
2.  A base de dados utilizada é o H2, configurado em memória, o que significa que os dados são perdidos a cada reinício da aplicação.
3.  O projeto utiliza o padrão de arquitetura em camadas (Controller, Service, Repository).
4.  **Este projeto não utiliza a biblioteca Lombok**. Todos os métodos `equals()`, `hashCode()`, `toString()`, construtores e *getters/setters* foram implementados manualmente.
