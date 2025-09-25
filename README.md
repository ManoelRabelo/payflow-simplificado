# PayFlow Simplificado 🏦

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![Maven](https://img.shields.io/badge/Maven-4.0-red)
![H2](https://img.shields.io/badge/Database-H2-orange)
<!--![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)-->
<!--![JUnit5](https://img.shields.io/badge/Tests-JUnit5%2FMockito-yellow)-->

O **PayFlow Simplificado** é uma aplicação de estudo desenvolvida em **Java + Spring Boot**, inspirada em sistemas de pagamento digital.  
O objetivo é permitir **transferências de valores entre usuários comuns e lojistas**, aplicando regras de negócio simplificadas para fins de aprendizado.

O projeto segue uma abordagem **orientada a microsserviços** e está sendo construído como parte de um **portfólio profissional**.

---

## 🛠️️ Stack e Dependências

Tecnologias e bibliotecas utilizadas neste projeto:

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.5.6
- **Banco de Dados:** H2 (desenvolvimento) / MySQL 8.0 (produção planejada)
- **Build:** Maven
- **IDE:** IntelliJ IDEA
- **Versionamento:** Git + GitHub

### 🗂️ Dependências principais

- `Spring Web` – Criação de APIs REST
- `Spring Data JPA` – Persistência com ORM
- `Spring Boot DevTools` – Facilita desenvolvimento com hot reload
- `Lombok` – Reduz boilerplate de código Java
- `OpenFeign` – Comunicação entre microsserviços via REST
- `H2 Database` – Banco de dados em memória para desenvolvimento local
- `Springdoc OpenAPI` – documentação interativa da API (Swagger)
- `JUnit 5 + Mockito` – testes unitários

---

## 🧱 Arquitetura da Aplicação

O sistema é organizado em **microsserviços independentes**, cada um com responsabilidade bem definida e comunicação via **APIs RESTful**.

### 👤 user-service
- Gerencia o cadastro de usuários e seus saldos (carteira).
- Expõe endpoints para consultar usuários e atualizar saldos.
- Responsável por validar tipo de usuário e saldo disponível.

### 💸 transaction-service
- Orquestra as transferências de valores entre usuários e lojistas.
- Valida regras de negócio mínimas (como limite de transferência e saldo disponível).
- Realiza chamadas ao serviço de autorização externa.
- Interage com `user-service` para débito/crédito e com `notification-service` para notificar o destinatário.

### 🔔 notification-service
- Simula o envio de notificações ao usuário recebedor da transferência.
- Implementado de forma resiliente para lidar com possíveis falhas no mock externo de notificação.

Cada microsserviço é desacoplado, seguindo **princípios de responsabilidade única**, preparado para **evolução modular e escalabilidade**.

> Diagramas de arquitetura serão adicionados futuramente.

---

## 📖 Documentação da API

- O projeto utiliza **Swagger/OpenAPI** para documentar e explorar endpoints.
- Ao rodar o serviço principal, a documentação estará disponível em:

http://localhost:8080/swagger-ui/index.html

---

## ✅ Testes

- **Ferramentas:** JUnit 5 e Mockito
- **Estratégia inicial:** testes unitários para regras de negócio principais
- **Plano futuro:** testes de integração entre microsserviços, testes de contrato e validação de fluxo completo

## ▶️ Execução do Projeto

### Passos para rodar localmente
1. **Clonar repositório:**
    ```bash
    git clone https://github.com/ManoelRabelo/payflow-simplificado.git
    cd payflow-simplificado
    ```
   
2. Configure o banco de dados no `application.properties` (usuário/senha).

3. Compile e rode o projeto:
   ```bash
    mvn spring-boot:run
    ```
4. Acesse os microsserviços nas portas configuradas (exemplo: 8080, 8081, 8082).

---

## 🚀 Melhorias Futuras

- Autenticação/Autorização (JWT/OAuth2)
- Docker e containerização dos microsserviços
- Service Discovery (Eureka/Consul)
- Resilience4j (circuit breaker/retry)
- Mensageria (RabbitMQ/Kafka)
- CI/CD automatizado
- Monitoramento e observabilidade (Prometheus, Grafana, Zipkin)

---

## 📚 Contribuições e Estudos

Este é um projeto de estudo e portfólio, criado para consolidar conhecimentos em:

- Arquitetura limpa e modular
- Microsserviços com Spring Boot
- Boas práticas de versionamento e documentação
- Testes unitários e futura cobertura de integração

Contribuições futuras são bem-vindas, assim como a evolução da aplicação para funcionalidades mais próximas de produção.

<!--

---

## 📌 Escopo Atual vs Futuro

### Implementado agora:
- Cadastro e consulta de usuários
- Transferências de valores com regras de negócio mínimas
- Persistência em H2 para desenvolvimento local
- Estrutura modular de microsserviços

### Não implementado neste momento:
- Persistência em MySQL
- Autenticação/autorização
- Docker e orquestração
- Mensageria
- Escalabilidade avançada

---

## 📋 Extras planejados

- Inserção de diagramas de arquitetura
- Tabela de endpoints com exemplos de requisições/respostas
- Fluxo de uso completo (exemplo de transferência)
- Changelog simples (histórico de evolução)
-->
