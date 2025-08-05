# 💰 FinPro – Backend

**Integrantes**
[Isabel Tenório](https://github.com/isabe1ltenorio) | [Iasmin Raquel](https://github.com/iasmin-raquel) | [Tayane Cibely](https://github.com/TayaneCibely) | [Leonardo Nunes](https://github.com/leonardonb) | [Izabel Nascimento](https://github.com/izabelnascimento) | [Letícia Lívia](https://github.com/mymph)

## 📃 Sobre o Projeto

FinPro é uma API REST desenvolvida com Spring Boot para gestão financeira pessoal, desenvolvida como parte da disciplina de **Projeto de Desenvolvimento** do curso de **Bacharelado em Ciência da Computação** da **UFAPE**, sob orientação do professor Rodrigo Gusmão de Carvalho Rocha, durante o **Período 2025.1**.

O sistema oferece funcionalidades  para controle financeiro.

Este backend se comunica com um frontend Angular, disponível [neste repositório](https://github.com/Projeto-Des-SW/finpro-front).

## 📍 Objetivos da API

- Fornecer endpoints seguros para autenticação de usuários
- Gerenciar perfis de usuário com diferentes níveis de acesso (USER/ADMIN)
- Processar e armazenar dados financeiros de forma segura
- Implementar validações de negócio para transações financeiras
- Oferecer documentação interativa com Swagger
- Garantir integridade dos dados através de validações robustas

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Gradle**

## 🧪 Pré-requisitos

- **Java 21** instalado
- **PostgreSQL** rodando localmente
- **IntelliJ IDEA** (preferência) ou qualquer IDE Java
- **Git** para clone do repositório

## ⚙️ Configuração

### 1. Clone o repositório:

```bash
git clone https://github.com/Projeto-Des-SW/finpro-service.git
cd finpro-service
```

### 2. Configure o banco de dados:

Crie o banco de dados PostgreSQL local utilizando as credenciais encontradas no [application.properties](src/main/resources/application.properties):

```properties
# Configurações do banco (example)
spring.datasource.url=jdbc:postgresql://localhost:5432/finpro_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

#### Via IntelliJ IDEA:

1. Deixe a IDE finalizar a importação das bibliotecas
2. Procure o main [FinproServiceApplication](src/main/java/com/ufape/finproservice/FinproServiceApplication.java)
3. Execute o main class

A API estará disponível em: **http://localhost:8080**

## 📂 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/ufape/finproservice/
│   │       ├── config/
│   │       │   ├── SecurityConfig.java
│   │       │   ├── SwaggerConfig.java
│   │       │   └── WebConfig.java
│   │       ├── controller/
│   │       │   ├── AdminController.java
│   │       │   ├── ExpenseController.java
│   │       │   └── UserController.java
│   │       ├── dto/
│   │       ├── enumeration/
│   │       ├── exception/
│   │       ├── mapper/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│       └── application.properties
└── test/
```

## 📚 Documentação da API

A documentação interativa está disponível através do Swagger:

**http://localhost:8080/swagger-ui/index.html**

## 🛡️ Segurança

- **JWT Authentication** - Tokens seguros para autenticação
- **BCrypt** - Hash seguro para senhas
- **CORS configurado** - Controle de acesso cross-origin
- **Roles baseadas** - Controle de acesso USER/ADMIN
- **Interceptor HTTP** - Validação automática de tokens

## 🗄️ Banco de Dados

O sistema utiliza PostgreSQL com as seguintes entidades principais:

## 📎 Links Relacionados

🎨 [Frontend do FinPro (Angular)](https://github.com/Projeto-Des-SW/finpro-front)

## 👨‍🏫 Professor Responsável

**Rodrigo Gusmão de Carvalho Rocha**  
Disciplina: Projeto de Desenvolvimento – UFAPE  
Período: 2025.1

## 📄 Licença

Este projeto está sendo desenvolvido para fins acadêmicos como parte da disciplina de Projeto de Desenvolvimento da UFAPE.
