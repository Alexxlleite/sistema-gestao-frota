# Sistema de Gestão de Frota de Veículos

Projeto inicial em Java Desktop com Swing, MySQL e JPA/EclipseLink.

## Como usar no Linux Mint com NetBeans
Ao executar o sistema pela primeira vez, o banco `sistema_frota`, as tabelas e o usuário administrador padrão podem ser criados automaticamente. O arquivo `database/sistema_frota.sql` está disponível como apoio para criação manual do banco, caso necessário.

Login inicial:

* Usuário: `admin`
* Senha: `admin`


## Pacotes do projeto

- `Apresentacao`: telas JFrame do sistema.
- `DataAccess`: classes DAO e conexão JPA.
- `DomainModel`: entidades do sistema.

## Observação

O projeto foi organizado com Maven para facilitar a abertura no NetBeans e baixar automaticamente as dependências:
EclipseLink, JPA API e MySQL Connector/J.
