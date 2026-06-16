# Sistema de Gestão de Frota de Veículos

Projeto inicial em Java Desktop com Swing, MySQL e JPA/EclipseLink.

## Como usar no Linux Mint com NetBeans

1. Abra o MySQL Workbench.
2. Execute o arquivo `database/sistema_frota.sql`.
3. Abra o NetBeans.
4. Vá em **Arquivo > Abrir Projeto** e selecione a pasta `SistemaFrota`.
5. Confira o arquivo `src/main/resources/META-INF/persistence.xml`.
   - Banco: `sistema_frota`
   - Usuário: `root`
   - Senha: deixe vazio ou coloque a senha do seu MySQL.
6. Execute a classe `Apresentacao.Main`.

## Login inicial sugerido

- Usuário: `admin`
- Senha: `admin`

## Pacotes do projeto

- `Apresentacao`: telas JFrame do sistema.
- `DataAccess`: classes DAO e conexão JPA.
- `DomainModel`: entidades do sistema.

## Observação

O projeto foi organizado com Maven para facilitar a abertura no NetBeans e baixar automaticamente as dependências:
EclipseLink, JPA API e MySQL Connector/J.
