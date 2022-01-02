# DEVinHouse - Projeto 4
**Uma api para cadastro de moradores em uma vila.**


Esta api consiste em rotas REST com controle de acesso utilizando token JWT.

As rotas disponíveis bem como o timpo de entrada e saída de dados está descrito abaixo;


## Rotas REST
### Autenticação
POST  - /login

Autenticação do usuário retornando o token JWT necessário para autenticações futuras

POST - /auth/forgot

Recuperação/geração de nova senha  que é enviada para diretamente para o email do usuáriop


POST - /auth/refresh_token

Revalidação do token existente gerando um novo token.


### Moradores
GET - /villager
Listagem de moradores

GET - /villager/{id}

Detalhes de um morador

GET - /villager/name?name=

Listagem de moradores filtrado por nome

GET - /villager/birth?month=JANUARY

Listagem de moradores filtrado por mês de aniversário

GET - /villager/age/{age}

Listagem de moradores filtrado por idade

POST - /create

Criação de usuário.


DELETE - /

Deleção de usuário.

### Relatório da vila
GET - /report/generate

Geração de relatório da fila.



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