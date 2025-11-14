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