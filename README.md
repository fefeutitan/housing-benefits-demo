# Housing Benefits Demo

API REST para demonstração de cadastro e consulta de candidatos a benefícios habitacionais.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Tests](https://img.shields.io/badge/tests-11%20passing-success)

## Stack

- Java 21
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA
- Jakarta Validation
- Flyway
- PostgreSQL 17
- Maven

## Como executar

### Pré-requisitos

- Java 21+
- Docker e Docker Compose

### Banco de dados

Suba o PostgreSQL:

```bash
docker compose up -d
```

Por padrão, a aplicação usa:

| Configuração | Valor |
| --- | --- |
| Host | `localhost` |
| Porta | `5433` |
| Banco | `housing_benefits` |
| Usuário | `housing` |
| Senha | `housing_dev` |

Para alterar a senha:

```bash
DB_PASSWORD=minha_senha docker compose up -d
```

### Aplicação

No Windows:

```powershell
.\\mvnw.cmd spring-boot:run
```

Em sistemas Unix-like:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Testes

```powershell
.\\mvnw.cmd clean test
```

O projeto possui testes de contexto, serviço, controller e integração com PostgreSQL.

## API

### Criar candidato

```http
POST /api/applicants
Content-Type: application/json
```

```json
{
  "name": "Maria Silva",
  "email": "maria@example.com"
}
```

Retorna `201 Created`. Dados inválidos retornam `400 Bad Request` e e-mail duplicado retorna `409 Conflict`.

### Listar candidatos

```http
GET /api/applicants
```

### Consultar candidato

```http
GET /api/applicants/{id}
```

Retorna `404 Not Found` quando o UUID não existe.

Mais detalhes estão disponíveis em [docs/api.md](docs/api.md).

## Estrutura

O código segue organização **package by feature**:

```text
src/main/java/dev/fefeutitan/housing_benefits_demo/
└── applicant/
    ├── Applicant.java
    ├── ApplicantRepository.java
    ├── ApplicantService.java
    ├── ApplicantController.java
    ├── CreateApplicantRequest.java
    └── ApplicantResponse.java
```

As migrations do banco ficam em `src/main/resources/db/migration`.
