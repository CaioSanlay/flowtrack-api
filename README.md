# FlowTrack API

API REST desenvolvida com Java e Spring Boot para gerenciamento de usuários, projetos e tarefas.

## Objetivo do projeto

O FlowTrack API foi criado como projeto de portfólio com foco em praticar o desenvolvimento de uma aplicação backend real utilizando Java, Spring Boot, JPA e PostgreSQL.

A aplicação permite:

- cadastrar usuários
- cadastrar projetos
- cadastrar tarefas
- relacionar tarefas com usuários e projetos
- filtrar tarefas por status e prioridade
- aplicar validações de entrada
- aplicar regras de negócio
- tratar exceções de forma padronizada

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven
- Bean Validation
- Postman
- Git e GitHub

## Funcionalidades principais

### Usuários
- criar usuário
- listar usuários
- buscar usuário por id
- deletar usuário

### Projetos
- criar projeto
- listar projetos
- buscar projeto por id
- deletar projeto

### Tarefas
- criar tarefa
- listar tarefas
- buscar tarefa por id
- atualizar tarefa
- deletar tarefa
- filtrar tarefas por status
- filtrar tarefas por prioridade

## Regras de negócio implementadas

- não permitir email duplicado
- não permitir tarefa duplicada para o mesmo usuário
- não permitir data limite no passado
- validar existência de usuário, projeto e tarefa
- aplicar status padrão `PENDING` quando não informado

## Validações implementadas

- campos obrigatórios com Bean Validation
- validação de email
- validação de prioridade obrigatória
- validação de usuário e projeto na criação de tarefas
- tratamento global de erros com respostas padronizadas

## Como executar o projeto

### Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- Java 17 ou superior
- Maven
- PostgreSQL
- IntelliJ IDEA, Eclipse ou STS
- Postman para testar os endpoints

### Configuração do banco de dados

Crie um banco no PostgreSQL com o nome:

```sql
CREATE DATABASE flowtrack;
````

 Depois, configure o arquivo application.properties com os dados do seu ambiente:

```properties
spring.application.name=flowtrack-api

spring.datasource.url=jdbc:postgresql://localhost:5432/flowtrack
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
````

## Como rodar a aplicação

#### 1. Clone o repositório:

```bash
git clone https://github.com/CaioSanlay/flowtrack-api.git
````
#### 2. Acesse a pasta do projeto:

````bash
cd flowtrack-api
````
#### 3. Abra o projeto na IDE de sua preferência.

#### 4. Execute a classe principal da aplicação.

Se tudo estiver certo, a API irá iniciar localmente em:

```
http://localhost:8080
````

## Estrutura do projeto

#### O projeto foi organizado em camadas para separar responsabilidades:

- controller → recebe as requisições HTTP
- service → concentra a regra de negócio
- repository → faz o acesso ao banco de dados
- entity → representa as entidades do sistema
- dto → organiza os dados de entrada e saída da API
- exception → centraliza o tratamento de erros
- enums → define valores fixos como status e prioridade

## Endpoints principais

### Usuários

#### Criar usuário
```http
POST/users
````
Exemplo de requisição

````json
{
  "name": "Caio",
  "email": "caio@email.com"
}
````
Exemplo de resposta

````json
{
  "id": 1,
  "name": "Caio",
  "email": "caio@email.com"
}
````
#### Listar usuários
```http
GET/users
````
#### Buscar usuário por id
````http
GET/users/{id}
````

#### Deletar usuário
````http
DELETE/users/{id}
````
### Projetos

#### Criar projeto
```http
POST/projects
````
Exemplo de requisição

````json
{
  "name": "FlowTrack API",
  "description": "Projeto backend para gerenciamento de tarefas"
}
````
Exemplo de resposta

````json
{
  "id": 1,
  "name": "FlowTrack API",
  "description": "Projeto backend para gerenciamento de tarefas"
}
````
#### Listar projetos
```http
GET/projects
````
#### Buscar projeto por id
````http
GET/projects/{id}
````

#### Deletar projeto
````http
DELETE/projects/{id}
````

### Tarefas

#### Criar tarefa
```http
POST/tasks
````
Exemplo de requisição

````json
{
  "title": "Criar controller",
  "description": "Implementar endpoints da aplicação",
  "dueDate": "2026-04-20",
  "priority": "HIGH",
  "user": {
    "id": 1
  },
  "project": {
    "id": 1
  }
}
````
Exemplo de resposta

````json
{
  "id": 1,
  "title": "Criar controller",
  "description": "Implementar endpoints da aplicação",
  "dueDate": "2026-04-20",
  "status": "PENDING",
  "priority": "HIGH",
  "userId": 1,
  "userName": "Caio",
  "projectId": 1,
  "projectName": "FlowTrack API"
}
````
#### Listar tarefas
```http
GET/tasks
````
#### Buscar tarefa por id
````http
GET/tasks/{id}
````
#### Atualizar tarefa
````http
PUT/tasks/{id}
````
Exemplo de requisição
````json
{
  "title": "Controller atualizado",
  "description": "Ajuste da tarefa",
  "dueDate": "2026-04-25",
  "status": "IN_PROGRESS",
  "priority": "MEDIUM",
  "user": {
    "id": 1
  },
  "project": {
    "id": 1
  }
}
````

#### Deletar tarefa
````http
DELETE/tasks/{id}
````
#### Filtrar tarefa por status
````http
GET/tasks/status/{status}
````
#### Exemplo
````http
GET/tasks/status/PENDING
````
#### Filtrar tarefa por prioridade
````http
GET/tasks/priority/{priority}
````
#### Exemplo
````http
GET/tasks/priority/HIGH
````

### Respostas de erro
A API também retorna respostas padronizadas para erros de validação e regras de negócio.

#### Exemplo de erro de validação
````json
{
  "timestamp": "2026-04-13T14:00:00",
  "status": 400,
  "error": "Validation error",
  "message": "One or more fields are invalid",
  "path": "/users",
  "fieldErrors": {
    "email": "Email must be valid"
  }
}
````
#### Exemplo de recurso não encontrado
````json
{
  "timestamp": "2026-04-13T14:00:00",
  "status": 404,
  "error": "Resource not found",
  "message": "Task not found",
  "path": "/tasks/99"
}
````
#### Exemplo de regra de negócio
````json
{
  "timestamp": "2026-04-13T14:00:00",
  "status": 400,
  "error": "Business rule violation",
  "message": "Due date cannot be in the past",
  "path": "/tasks"
}
````
## Melhorias futuras

Este projeto foi desenvolvido com foco em consolidar fundamentos importantes do backend com Java e Spring Boot, dentro de um nível sólido para portfólio júnior.

Como possíveis evoluções futuras, a aplicação pode receber melhorias como:

- autenticação e autorização com Spring Security
- documentação automática com Swagger/OpenAPI
- paginação de resultados
- testes unitários e de integração
- deploy em nuvem
- containerização com Docker

## Aprendizados aplicados no projeto

Durante o desenvolvimento do FlowTrack API, foram praticados conceitos importantes como:

- arquitetura em camadas
- modelagem de entidades com relacionamentos
- uso de DTOs para entrada e saída de dados
- validação com Bean Validation
- tratamento global de exceções
- uso de ResponseEntity para respostas HTTP mais adequadas
- implementação de regras de negócio no service
- integração com banco de dados relacional usando JPA e PostgreSQL
- versionamento com Git e GitHub

## Conclusão

O FlowTrack API foi um projeto desenvolvido para consolidar conhecimentos fundamentais de backend com Java e Spring Boot por meio da construção de uma API REST completa.

Ao longo do desenvolvimento, foram aplicados conceitos importantes como arquitetura em camadas, persistência de dados com JPA, integração com PostgreSQL, uso de DTOs, validações com Bean Validation, tratamento global de exceções, regras de negócio e padronização de respostas HTTP.

Mais do que apenas implementar um CRUD, este projeto permitiu praticar a construção de uma aplicação com regras reais, organização de código e preocupação com clareza, manutenção e qualidade da API, servindo como uma base sólida para evolução como desenvolvedor backend júnior.

## Autor

Desenvolvido por **Caio Sanlay** como projeto de portfólio para demonstrar conhecimentos em desenvolvimento backend com Java.