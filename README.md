# URL Shortener - API (Backend)

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.pt-BR.md)

This project consists of a RESTful API for a URL shortening service. The solution was designed with a focus on high availability, horizontal scalability, and cloud security.

## 🚀 Technologies Used

- **Language:** Java 17
- **Framework:** Spring Boot 3.2.0
- **Persistence:** Spring Data JPA / Hibernate
- **Security:** Spring Security (CORS configuration and public route release)
- **Secrets Management:** AWS Secrets Manager
- **Containerization:** Docker
- **Cloud Infrastructure:** AWS ECS (Elastic Container Service) with Fargate (Serverless) and Application Load Balancer (ALB)
- **Database:** PostgreSQL (Hosted on AWS RDS)
- **Local Database:** H2 Database (for development)

---

## 🏛️ Architecture and Design Decisions

The application was structured following the principles of:

- **Stateless:** The API does not maintain state (sessions or local files). All persistence is delegated to the relational database and reading sensitive configurations is done via AWS Secrets Manager.
- **Auto-scalable:** The deployment on AWS Fargate has *Auto Scaling* policies based on CPU utilization, allowing the application to grow horizontally according to access demand.
- **High Availability (SLA 99%):** The application is positioned behind an *Application Load Balancer* (ALB), ensuring load distribution and continuous *Health Checks* execution to isolate failed instances.
- **Network Isolation (Security Groups):** The RDS database is in an isolated layer and only accepts connections from the ECS application security group. The application, in turn, only accepts traffic from the Load Balancer.

---

## 🛠️ How to Run the Project Locally

### Prerequisites
- Java 17 installed
- Maven installed

---

### 🔹 Option 1: Run with H2 Database (Recommended for Local Development)

This option uses an in-memory H2 database, without the need for AWS or PostgreSQL.

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repository.git
   cd your-repository
   ```

2. Run the application with the local profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
   ```

3. Access the application:
   - **API:** http://localhost:8080
   - **H2 Console:** http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:urlshortener`
     - Username: `sa`
     - Password: (leave blank)

---

### 🔹 Option 2: Run with PostgreSQL (AWS RDS)

This option connects to the PostgreSQL database hosted on AWS.

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repository.git
   cd your-repository
   ```

2. Configure AWS credentials in your terminal or environment:
   ```bash
   export AWS_ACCESS_KEY_ID="your_access_key"
   export AWS_SECRET_ACCESS_KEY="your_secret_key"
   export AWS_REGION="us-east-1"
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## ☁️ Deploying to AWS with Terraform

### Prerequisites
- AWS CLI configured with appropriate credentials
- Terraform installed (>= 1.5.0)
- Docker image pushed to AWS ECR

### Steps

1. Navigate to the terraform directory:
   ```bash
   cd terraform
   ```

2. Create your variables file from the example:
   ```bash
   cp terraform.tfvars.example terraform.tfvars
   ```

3. Edit `terraform.tfvars` and configure:
   - `container_image`: Your ECR repository URI
   - `db_password`: A strong password for the RDS database
   - Other variables as needed

4. Initialize Terraform:
   ```bash
   terraform init
   ```

5. Review the infrastructure plan:
   ```bash
   terraform plan
   ```

6. Apply the infrastructure:
   ```bash
   terraform apply
   ```
---
