-- O Flyway vai automaticamente criar o bando de dados.

DROP DATABASE jobfitscore;
CREATE DATABASE jobfitscore;

--------------------------------------

CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(200) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    refresh_token VARCHAR(200),
    expiracao_refresh_token TIMESTAMP
);

CREATE TABLE empresas (
    id_empresa SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(200) NOT NULL,
    refresh_token VARCHAR(200),
    expiracao_refresh_token TIMESTAMP
);

CREATE TABLE vagas (
    id_vaga SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    empresa_id INT NOT NULL REFERENCES empresas(id_empresa)
);

CREATE TABLE habilidades (
    id_habilidade SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuario_habilidade (
    id_usuario_habilidade SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    habilidade_id INT NOT NULL REFERENCES habilidades(id_habilidade) ON DELETE CASCADE,
    CONSTRAINT unq_usuario_habilidade UNIQUE (usuario_id, habilidade_id)
);

CREATE TABLE cursos (
    id_curso SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    instituicao VARCHAR(150),
    carga_horaria INT,
    usuario_id INT NOT NULL REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE candidaturas (
    id_candidatura SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    vaga_id INT NOT NULL REFERENCES vagas(id_vaga) ON DELETE CASCADE,
    data_candidatura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'Em Análise',
    CONSTRAINT chk_status CHECK (status IN ('Em Análise', 'Triagem', 'Aprovado', 'Reprovado'))
);

CREATE TABLE vaga_habilidade (
    id_vaga_habilidade SERIAL PRIMARY KEY,
    vaga_id INT NOT NULL REFERENCES vagas(id_vaga) ON DELETE CASCADE,
    habilidade_id INT NOT NULL REFERENCES habilidades(id_habilidade) ON DELETE CASCADE,
    CONSTRAINT unq_vaga_habilidade UNIQUE (vaga_id, habilidade_id)
);

--------------------------------------

INSERT INTO usuarios (nome, email, senha, is_admin, refresh_token, expiracao_refresh_token) VALUES
-- senha: joaogab
('João Gabriel', 'joao.gabriel@jobfitscore.com', '$2a$12$Y9yI0rCtMvF3UXiPG0vzDuPz4nsM2nOGFFx9lgvOir/ozaltrEA.a', FALSE, NULL, NULL),
-- senha: mariasilva
('Maria Silva', 'maria.silva@jobfitscore.com', '$2a$12$FWRuZg1nc8Yd0i.NXvNFz.n8ncUw5xSA.NbvGKhF1wlf3dHktNkqO', FALSE, NULL, NULL),
-- senha: pedroalves
('Pedro Alves', 'pedro.alves@jobfitscore.com', '$2a$12$aJa2FDDauhWn0ix82JvnTeuNpzW5vR9JgogJ8/6GRzIKfRjW8WXKq', FALSE, NULL, NULL);

INSERT INTO empresas (nome, cnpj, email, senha, refresh_token, expiracao_refresh_token) VALUES
-- senha: xptotech
('XPTO TECH', '87797759000125', 'contato@xptotech.com', '$2a$12$Na99bXiMtG2iruVrlPdnZe0CgYahR/sMe/r7SM/voKImz52BByvNq', NULL, NULL),
-- senha: nextinnovation
('NEXT INNOVATION', '58213476000188', 'contato@nextinnovation.com', '$2a$12$/yKwHJ01PIup8hRaxg8uGOoeNQ9pTFKk/Z/cF9lU5H6H29C2et5Yq', NULL, NULL),
-- senha: techvision
('TECH VISION', '12897564000102', 'suporte@techvision.com', '$2a$12$8GWa9MHFoUXdUASw5MvOIe0hKvNzdzlxLXhERakrWfDhRK4SSAPQm', NULL, NULL);

INSERT INTO vagas (titulo, empresa_id) VALUES
('Desenvolvedor Java Pleno', 1),
('Analista de Dados', 2),
('DevOps Engineer', 3);

INSERT INTO habilidades (nome) VALUES
('Java'),
('Spring Boot'),
('PostgreSQL');

INSERT INTO usuario_habilidade (usuario_id, habilidade_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO cursos (nome, instituicao, carga_horaria, usuario_id) VALUES
('Engenharia de Software', 'FIAP', 4000, 1),
('Banco de Dados Avançado', 'Alura', 120, 2),
('APIs REST com Spring Boot', 'FIAP', 100, 3);

INSERT INTO candidaturas (usuario_id, vaga_id, status) VALUES
(1, 1, 'Em Análise'),
(2, 2, 'Triagem'),
(3, 3, 'Aprovado');

INSERT INTO vaga_habilidade (vaga_id, habilidade_id) VALUES
(1, 1),
(2, 2),
(3, 3);

--------------------------------------

--senha: admin
INSERT INTO usuarios (nome, email, senha, is_admin) VALUES
('Administrador', 'admin@jobfitscore.com', '$2a$12$aJa2FDDauhWn0ix82JvnTeuNpzW5vR9JgogJ8/6GRzIKfRjW8WXKq', TRUE);