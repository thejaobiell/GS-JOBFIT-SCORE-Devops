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