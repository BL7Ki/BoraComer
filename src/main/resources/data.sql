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
VALUES ('Admin', 'admin@example.com', 'admin', '$2a$10$WkxC5by7FW1dxnZZMSIWf.0ep0dZ01OLjsZVt8iYhwq6TOUk4N9Rq', CURRENT_TIMESTAMP, null, 'ADMIN', 'Rua Principal', 'Centro', 'Cidade Exemplo', 'SP', '12345-678');

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

-- Criação da tabela de itens do cardápio V4
CREATE TABLE IF NOT EXISTS tb_menu_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10, 2) NOT NULL,
    delivery BOOLEAN NOT NULL,
    imagem_caminho VARCHAR(255),
    restaurante_id BIGINT NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_restaurante_menu_item FOREIGN KEY (restaurante_id) REFERENCES tb_restaurantes(id)
);

-- Exemplo de item do cardápio se quiser
INSERT INTO tb_menu_items (nome, descricao, preco, delivery, imagem_caminho, restaurante_id)
VALUES
('Temaki de Salmão', 'Temaki recheado com salmão fresco e cebolinha', 24.90, false, '/imagens/temaki.jpg', 1),
('Sashimi Especial', 'Fatias selecionadas de salmão e atum', 39.90, true, '/imagens/sashimi.jpg', 1);


CREATE TABLE IF NOT EXISTS tb_pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery BOOLEAN NOT NULL,
    restaurante_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pedido_resturante FOREIGN KEY (restaurante_id) REFERENCES tb_restaurantes(id),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id)
);

-- Exemplo de pedido - Incluindo o status padrão
INSERT INTO tb_pedidos (data_hora, delivery, restaurante_id, usuario_id, status)
VALUES (CURRENT_TIMESTAMP, true, 1, 1, 'PENDING');

-- Criação da tabela de pedidos_itens

CREATE TABLE IF NOT EXISTS tb_pedidos_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantidade BIGINT NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pedido_item_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id),
    CONSTRAINT fk_pedido_item_menu_item FOREIGN KEY (menu_item_id) REFERENCES tb_menu_items(id)
);
-- Exemplo de pedido item (opcional)
INSERT INTO tb_pedidos_items (pedido_id, menu_item_id, quantidade, data_alteracao)
VALUES (1, 1, 2, CURRENT_TIMESTAMP);


-- Criação da tabela de reservas

CREATE TABLE IF NOT EXISTS tb_reservas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quantidade INT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reserva_resturante FOREIGN KEY (restaurante_id) REFERENCES tb_restaurantes(id),
    CONSTRAINT fk_reserva_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id)
);

-- Exemplo de reserva (opcional)
INSERT INTO tb_reservas (data_hora, quantidade, restaurante_id, usuario_id)
VALUES ('2025-01-01 22:00:00', 2, 1, 1);

INSERT INTO tb_usuarios (nome, email, login, senha, data_criacao, data_alteracao, role, rua, bairro, cidade, estado, cep, tipo_usuario_id)
VALUES ('Teste', 'teste@example.com', 'lucastorres', '$2a$10$WkxC5by7FW1dxnZZMSIWf.0ep0dZ01OLjsZVt8iYhwq6TOUk4N9Rq', CURRENT_TIMESTAMP, null, 'ADMIN', 'Rua Principal', 'Centro', 'Cidade Exemplo', 'SP', '12345-678', 1);

INSERT INTO tb_usuarios (nome, email, login, senha, data_criacao, data_alteracao, role, rua, bairro, cidade, estado, cep, tipo_usuario_id)
VALUES ('Testedois', 'testedois@example.com', 'lucastorresdois', '$2a$10$WkxC5by7FW1dxnZZMSIWf.0ep0dZ01OLjsZVt8iYhwq6TOUk4N9Rq', CURRENT_TIMESTAMP, null, 'ADMIN', 'Rua Principal', 'Centro', 'Cidade Exemplo', 'SP', '12345-678', 2);
