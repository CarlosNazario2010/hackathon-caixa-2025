# Sistema de Simulação de Empréstimo

Este projeto é uma API REST em Spring Boot para simular empréstimos com base em diferentes sistemas de amortização: **SAC** (Sistema de Amortização Constante) e **PRICE** (Sistema Francês de Amortização).

O sistema determina automaticamente o produto de empréstimo e a taxa de juros com base nos valores e prazos solicitados.

---

### 🚀 Como Rodar o Projeto

1.  **Pré-requisitos:**
    * Java 17 ou superior
    * Maven
    * Um ambiente de desenvolvimento como o IntelliJ IDEA ou VS Code

2.  **Clonar o Repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd [pasta_do_projeto]
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

-   **Regras de Negócio:** O produto e a taxa de juros são definidos com base nos dados de entrada.
    * **Produto 1:** `valorDesejado` entre R$ 200 e R$ 10.000 OU `prazo` entre 0 e 24 meses.
    * **Produto 2:** `prazo` entre 25 e 48 meses OU `valorDesejado` entre R$ 10.000,01 e R$ 100.000.
    * **Produto 3:** `prazo` entre 49 e 96 meses OU `valorDesejado` entre R$ 100.000,01 e R$ 1.000.000.
    * **Produto 4:** `valorDesejado` acima de R$ 1.000.000 OU `prazo` maior que 96 meses.

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

-   **Resposta de Erro (400 Bad Request):**
    Ocorre se os dados de entrada forem inválidos (nulos ou fora do mínimo permitido).

    ```json
    {
      "timestamp": "2025-08-20T22:30:00.000+00:00",
      "status": 400,
      "error": "Bad Request",
      "path": "/api/simulacao"
    }
    ```