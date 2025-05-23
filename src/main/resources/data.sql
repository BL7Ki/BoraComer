CREATE TABLE IF NOT EXISTS tb_usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_alteracao TIMESTAMP NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(50) NOT NULL,
    rua VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(2),
    cep VARCHAR(10)
);

INSERT INTO tb_usuarios (nome, email, login, senha, data_alteracao, role, rua, bairro, cidade, estado, cep)
VALUES ('Admin', 'admin@example.com', 'admin', 'senhaAdmin123', CURRENT_TIMESTAMP, 'ADMIN', 'Rua Principal', 'Centro', 'Cidade Exemplo', 'SP', '12345-678');