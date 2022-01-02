# DEVinHouse - Projeto 4
**Uma api para cadastro de moradores em uma vila.**


Esta api consiste em rotas REST com controle de acesso utilizando token JWT.

As rotas disponíveis bem como o timpo de entrada e saída de dados está descrito abaixo;


## Rotas REST
### Autenticação
POST  - /login

Rota responsável por autenticar o usuário retornando e retornar o token JWT necessário para autenticações futuras

POST - /auth/forgot

POST - /auth/refresh_token

### Moradores
GET - /villager
GET - /villager/{id}
GET - /villager/name?name=
GET - /villager/birth?month=JANUARY
GET - /villager/age/{age}
POST - /create
DELETE - /

### Relatório da vila
GET - /report/generate


Na raiz do projeto temos o PDF brModeloWeb.pdf demonstrando nossa estrutura de banco.

Este projeto foi implementado utilizando:
* Spring Boot - v2.6.1
* Spring Web - v2.6.1
* Spring Mail - v2.6.1
* Spring Security - v5.6.0
* Postgresql - v42.3.1
* C3p0 - v0.9.5.5
* Jjwt - v0.9.1
* gson - v2.8.9
* Flyway-core - v8.3.0