# Encurtador de Links - API (Backend)

Este projeto consiste em uma API RESTful para um serviço de encurtamento de URLs. A solução foi desenhada com foco em alta disponibilidade, escalabilidade horizontal e segurança na nuvem.

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.2.0
- **Persistência:** Spring Data JPA / Hibernate
- **Segurança:** Spring Security (Configuração de CORS e liberação de rotas públicas)
- **Gerenciamento de Segredos:** AWS Secrets Manager
- **Containerização:** Docker
- **Infraestrutura Cloud:** AWS ECS (Elastic Container Service) com Fargate (Serverless) e Application Load Balancer (ALB)
- **Banco de Dados:** PostgreSQL (Hospedado no AWS RDS)
- **Banco de Dados Local:** H2 Database (para desenvolvimento)

---

## 🏛️ Arquitetura e Decisões de Projeto

A aplicação foi estruturada seguindo os princípios de:

- **Stateless:** A API não mantém estado (sessões ou arquivos locais). Toda a persistência é delegada ao banco de dados relacional e a leitura de configurações sensíveis é feita via AWS Secrets Manager.
- **Auto-escalável:** O deploy no AWS Fargate conta com políticas de *Auto Scaling* baseadas na utilização de CPU, permitindo que a aplicação cresça horizontalmente conforme a demanda de acessos.
- **Alta Disponibilidade (SLA 99%):** A aplicação está posicionada atrás de um *Application Load Balancer* (ALB), garantindo a distribuição de carga e execução de *Health Checks* contínuos para isolar instâncias falhas.
- **Isolamento de Redes (Security Groups):** O banco de dados RDS está em uma camada isolada e só aceita conexões vindas do grupo de segurança da aplicação ECS. A aplicação, por sua vez, só aceita tráfego vindo do Load Balancer.

---

## 🛠️ Como Executar o Projeto Localmente

### Pré-requisitos
- Java 17 instalado
- Maven instalado

---

### 🔹 Opção 1: Executar com Banco H2 (Recomendado para Desenvolvimento Local)

Esta opção usa um banco de dados H2 em memória, sem necessidade de AWS ou PostgreSQL.

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```

2. Execute a aplicação com o profile local:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
   ```

3. Acesse a aplicação:
   - **API:** http://localhost:8080
   - **Console H2:** http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:urlshortener`
     - Username: `sa`
     - Password: (deixe em branco)

---

### 🔹 Opção 2: Executar com PostgreSQL (AWS RDS)

Esta opção conecta ao banco PostgreSQL hospedado na AWS.

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```

2. Configure as credenciais da AWS no seu terminal ou ambiente:
   ```bash
   export AWS_ACCESS_KEY_ID="sua_access_key"
   export AWS_SECRET_ACCESS_KEY="sua_secret_key"
   export AWS_REGION="us-east-1"
   ```

3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

---
