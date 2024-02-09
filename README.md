# Boardcamp API

Este é um projeto de API para gerenciar jogos, clientes e aluguéis de uma loja de jogos. A aplicação foi desenvolvida usando Java 17 e Spring Boot, com persistência de dados em um banco PostgreSQL.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.2
- PostgreSQL
- Hibernate (JPA)
- Maven

## Estrutura do Projeto

A estrutura do projeto segue o padrão MVC (Model-View-Controller) com as seguintes camadas:

- `controllers`: Controladores que lidam com as requisições HTTP.
- `services`: Lógica de negócios e interação com os repositórios.
- `repositories`: Repositórios para interação com o banco de dados.
- `models`: Entidades e objetos de domínio.
- `dtos`: Objetos de Transferência de Dados para comunicação com a API.

## Endpoints

### Jogos (Games)

- **Listar jogos**
  - **Rota:** `GET /games`
  - **Resposta:**
    ```json
    [
      {
        "id": 1,
        "name": "Banco Imobiliário",
        "image": "http://www.imagem.com.br/banco_imobiliario.jpg",
        "stockTotal": 3,
        "pricePerDay": 1500
      },
      {
        "id": 2,
        "name": "Detetive",
        "image": "http://www.imagem.com.br/detetive.jpg",
        "stockTotal": 1,
        "pricePerDay": 2500
      }
    ]
    ```

- **Inserir um jogo**
  - **Rota:** `POST /games`
  - **Corpo da Requisição:**
    ```json
    {
      "name": "Banco Imobiliário",
      "image": "http://www.imagem.com.br/banco_imobiliario.jpg",
      "stockTotal": 3,
      "pricePerDay": 1500
    }
    ```
  - **Resposta:**
    ```json
    {
      "id": 1,
      "name": "Banco Imobiliário",
      "image": "http://www.imagem.com.br/banco_imobiliario.jpg",
      "stockTotal": 3,
      "pricePerDay": 1500
    }
    ```

### Clientes (Customers)

- **Buscar um cliente por id**
  - **Rota:** `GET /customers/:id`
  - **Resposta:**
    ```json
    {
      "id": 1,
      "name": "João Alfredo",
      "cpf": "01234567890"
    }
    ```

- **Inserir um cliente**
  - **Rota:** `POST /customers`
  - **Corpo da Requisição:**
    ```json
    {
      "name": "João Alfredo",
      "cpf": "01234567890"
    }
    ```
  - **Resposta:**
    ```json
    {
      "id": 1,
      "name": "João Alfredo",
      "cpf": "01234567890"
    }
    ```

### Aluguéis (Rentals)

- **Listar aluguéis**
  - **Rota:** `GET /rentals`
  - **Resposta:**
    ```json
    [
      {
        "id": 1,
        "rentDate": "2021-06-20",
        "daysRented": 3,
        "returnDate": null,
        "originalPrice": 4500,
        "delayFee": 0,
        "customer": {
          "id": 1,
          "name": "João Alfredo",
          "cpf": "01234567890"
        },
        "game": {
          "id": 1,
          "name": "Banco Imobiliário",
          "image": "http://www.imagem.com.br/banco.jpg",
          "stockTotal": 3,
          "pricePerDay": 1500
        }
      }
    ]
    ```

- **Inserir um aluguel**
  - **Rota:** `POST /rentals`
  - **Corpo da Requisição:**
    ```json
    {
      "customerId": 1,
      "gameId": 1,
      "daysRented": 3
    }
    ```
  - **Resposta:**
    ```json
    {
      "id": 1,
      "rentDate": "2021-06-20",
      "daysRented": 3,
      "returnDate": null,
      "originalPrice": 4500,
      "delayFee": 0,
      "customer": {
        "id": 1,
        "name": "João Alfredo",
        "cpf": "01234567890"
      },
      "game": {
        "id": 1,
        "name": "Banco Imobiliário",
        "image": "http://www.imagem.com.br/banco.jpg",
        "stockTotal": 3,
        "pricePerDay": 1500
      }
    }
    ```

- **Finalizar aluguel**
  - **Rota:** `PUT /rentals/:id/return`
  - **Resposta:**
    ```json
    {
      "id": 1,
      "rentDate": "2021-06-20",
      "daysRented": 3,
      "returnDate": "2021-06-25",
      "originalPrice": 4500,
      "delayFee": 3000,
      "customer": {
        "id": 1,
        "name": "João Alfredo",
        "cpf": "01234567890"
      },
      "game": {
        "id": 1,
        "name": "Banco Imobiliário",
        "image": "http://www.imagem.com.br/banco.jpg",
        "stockTotal": 3,
        "pricePerDay": 1500
      }
    }
    ```

## Executando a Aplicação

1. Clone o repositório: `git clone https://github.com/seu-usuario/nome-do-repositorio.git`
2. Configure as variáveis de ambiente no arquivo `.env`.
3. Execute o aplicativo: `mvn spring-boot:run`

Deploy [link](https://boardcamp-api-ofw1.onrender.com)
