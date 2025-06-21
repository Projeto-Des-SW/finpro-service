# 💰 FinPro – Backend

FinPro é uma API REST desenvolvida com Spring Boot para gestão financeira pessoal, incluindo funcionalidades como
receitas, despesas, metas e projeção de investimentos.

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gradle

## 🧪 Pré-requisitos

- Java 21
- PostgreSQL rodando localmente
- Intellij (preferência)

## ⚙️ Configuração

1. Clone o repositório:
2. Crie o banco de dados local com Postgres utilizando as credenciais encontradas no
[application.properties](src/main/resources/application.properties)
3. Rodar via terminal na raiz do projeto:
   ```
   ./gradlew build
   ./gradlew bootRun
   ```
4. Rodar pelo Intellij:
   1. Deixe a IDE finalizar a importação das bibliotecas, após a finalização:
   2. Procure o main [FinproServiceApplication](src/main/java/com/ufape/finproservice/FinproServiceApplication.java)
   3. Dê run no main




