-- Criação da tabela de categorias de produtos V1
CREATE TABLE IF NOT EXISTS tb_usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP,
    role VARCHAR(50) NOT NULL,
    rua VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(10)
);

INSERT INTO tb_usuarios (nome, email, login, senha, data_criacao, data_alteracao, role, rua, bairro, cidade, estado, cep)
VALUES ('Admin', 'admin@example.com', 'admin', 'senhaAdmin123', CURRENT_TIMESTAMP, null, 'ADMIN', 'Rua Principal', 'Centro', 'Cidade Exemplo', 'SP', '12345-678');

-- Criação da tabela de tipos de usuários e relacionamento com a tabela de usuários V2
CREATE TABLE IF NOT EXISTS tb_tipo_usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_usuario VARCHAR(100) UNIQUE
);

ALTER TABLE tb_usuarios ADD COLUMN IF NOT EXISTS tipo_usuario_id BIGINT;

ALTER TABLE tb_usuarios
    ADD CONSTRAINT IF NOT EXISTS fk_tipo_usuario
    FOREIGN KEY (tipo_usuario_id) REFERENCES tb_tipo_usuarios(id);

INSERT INTO tb_tipo_usuarios (tipo_usuario) VALUES ('DONO_RESTAURANTE');
INSERT INTO tb_tipo_usuarios (tipo_usuario) VALUES ('CLIENTE');

-- Criação da tabela de restaurantes V3
CREATE TABLE IF NOT EXISTS tb_restaurantes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    tipo_cozinha VARCHAR(50) NOT NULL,
    horario_funcionamento VARCHAR(50) NOT NULL,
    dono_id BIGINT NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_dono_restaurante FOREIGN KEY (dono_id) REFERENCES tb_usuarios(id)
);

-- Exemplo de restaurante (opcional)
INSERT INTO tb_restaurantes (nome, endereco, tipo_cozinha, horario_funcionamento, dono_id)
VALUES ('Sushi Brasil', 'Av. Paulista, 1000', 'Japonesa', '18:00 - 23:00', 1);
