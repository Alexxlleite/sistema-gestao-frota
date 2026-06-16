CREATE DATABASE IF NOT EXISTS sistema_frota
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE sistema_frota;

CREATE TABLE IF NOT EXISTS usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS veiculo (
    idVeiculo INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    tipo VARCHAR(50),
    cor VARCHAR(30),
    quilometragemAtual DOUBLE DEFAULT 0,
    status VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS motorista (
    idMotorista INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    cnh VARCHAR(20) NOT NULL UNIQUE,
    categoriaCnh VARCHAR(5) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(150),
    status VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS viagem (
    idViagem INT AUTO_INCREMENT PRIMARY KEY,
    veiculo_id INT NOT NULL,
    motorista_id INT NOT NULL,
    localSaida VARCHAR(100) NOT NULL,
    localDestino VARCHAR(100) NOT NULL,
    dataHoraSaida VARCHAR(30),
    dataHoraRetorno VARCHAR(30),
    quilometragemInicial DOUBLE DEFAULT 0,
    quilometragemFinal DOUBLE DEFAULT 0,
    observacoes VARCHAR(255),
    status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_viagem_veiculo FOREIGN KEY (veiculo_id) REFERENCES veiculo(idVeiculo),
    CONSTRAINT fk_viagem_motorista FOREIGN KEY (motorista_id) REFERENCES motorista(idMotorista)
);

CREATE TABLE IF NOT EXISTS manutencao (
    idManutencao INT AUTO_INCREMENT PRIMARY KEY,
    veiculo_id INT NOT NULL,
    tipoManutencao VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    dataManutencao VARCHAR(30),
    quilometragem DOUBLE DEFAULT 0,
    valor DOUBLE DEFAULT 0,
    oficina VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_manutencao_veiculo FOREIGN KEY (veiculo_id) REFERENCES veiculo(idVeiculo)
);

CREATE TABLE IF NOT EXISTS abastecimento (
    idAbastecimento INT AUTO_INCREMENT PRIMARY KEY,
    veiculo_id INT NOT NULL,
    motorista_id INT,
    tipoCombustivel VARCHAR(30) NOT NULL,
    quantidadeLitros DOUBLE NOT NULL,
    valorTotal DOUBLE NOT NULL,
    dataAbastecimento VARCHAR(30),
    quilometragem DOUBLE DEFAULT 0,
    CONSTRAINT fk_abastecimento_veiculo FOREIGN KEY (veiculo_id) REFERENCES veiculo(idVeiculo),
    CONSTRAINT fk_abastecimento_motorista FOREIGN KEY (motorista_id) REFERENCES motorista(idMotorista)
);

INSERT INTO usuario (nome, login, senha, perfil, status)
SELECT 'Administrador', 'admin', 'admin', 'Administrador', 'Ativo'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE login = 'admin');
