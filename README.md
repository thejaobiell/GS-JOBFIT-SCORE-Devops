# 🎯 JobFitScore

## 📋 Sobre o Projeto

JobFitScore é uma plataforma que facilita o processo de recrutamento através de um sistema de pontuação baseado em habilidades. O sistema permite que empresas publiquem vagas, candidatos se inscrevam e um algoritmo inteligente calcula a compatibilidade entre perfis e oportunidades.

## ✨ Funcionalidades

### 👤 Para Usuários (Candidatos)
- ✅ Cadastro e autenticação com JWT
- ✅ Gerenciamento de perfil profissional
- ✅ Registro de habilidades técnicas
- ✅ Cadastro de cursos e formações
- ✅ Candidatura em vagas
- ✅ Acompanhamento de status de candidaturas
- ✅ Sistema de pontuação (Score) baseado em match de habilidades

### 🏢 Para Empresas
- ✅ Cadastro e autenticação com JWT
- ✅ Publicação de vagas de emprego
- ✅ Definição de habilidades requeridas por vaga
- ✅ Visualização de candidatos por vaga
- ✅ Análise automática de compatibilidade candidato-vaga
- ✅ Gerenciamento de processos seletivos

### 👨‍💼 Para Administradores
- ✅ Acesso universal a todos os endpoints
- ✅ Gerenciamento completo de usuários e empresas
- ✅ Controle total da plataforma
- ✅ Hierarquia de permissões com Spring Security

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.7** - Framework principal
- **Spring Security 6.5.6** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - ORM
- **JWT (Auth0)** - Tokens de autenticação
- **BCrypt** - Criptografia de senhas

### Banco de Dados
- **PostgreSQL 16.10** - Banco de dados relacional
- **Flyway** - Versionamento e migração de schema

### Ferramentas
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate
- **Bean Validation** - Validação de dados

## 🏗️ Arquitetura

### Estrutura do Projeto
```
src/main/java/com/gs/fiap/jobfitscore/
├── controller/              # Endpoints REST
├── domain/
│   ├── autenticacao/       # Lógica de autenticação e JWT
│   ├── usuario/            # Entidades e serviços de usuários
│   ├── usuariohabilidade/  # Entidades e serviços de usuáriohabiliadade
│   ├── empresa/            # Entidades e serviços de empresas
│   ├── habilidade/         # Entidades e serviços de habilidades
│   ├── curso/              # Entidades e serviços de cursos
│   ├── candidatura/        # Entidades e serviços de candidaturas
│   ├── vaga/               # Entidades e serviços de vagas
│   └── vagahabilidade/     # Entidades e serviços de vagahabilidade
├── infra/
│   ├── config/             # Configurações do cache
│   ├── swagger/            # Configurações do swagger
│   ├── security/           # Configurações de segurança
│   └── exception/          # Tratamento de exceções
└── JobfitscoreApplication  # Classe principal
```

### Modelo de Dados

#### Entidades Principais
- **usuarios** - Dados dos candidatos
- **empresas** - Dados das empresas
- **vagas** - Vagas publicadas pelas empresas
- **habilidades** - Habilidades técnicas
- **cursos** - Formações dos usuários
- **candidaturas** - Relação usuário-vaga
- **usuario_habilidade** - Habilidades dos usuários
- **vaga_habilidade** - Habilidades requeridas por vaga

## 🔐 Hierarquia de Roles

```
┌─────────────┐
│    ADMIN    │  ← Acesso total à aplicação
└──────┬──────┘
       │ herda permissões de
   ┌───┴───────┐
   │           │
┌──▼────┐   ┌──▼────┐
│USUARIO│   │EMPRESA│  ← Mesmo nível, sem herança entre si
└───────┘   └───────┘
```

### Permissões por Role

| Endpoint | ADMIN | USUARIO | EMPRESA |
|----------|-------|---------|---------|
| `/api/usuarios/**` | ✅ | ✅ | ❌ |
| `/api/empresas/**` | ✅ | ❌ | ✅ |
| `/api/vagas/**` | ✅ | ✅ | ✅ |
| `/api/cursos/**` | ✅ | ✅ | ✅ |
| `/api/habilidades/**` | ✅ | ✅ | ✅ |
| `/api/candidaturas/**` | ✅ | ✅ | ✅ |

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- PostgreSQL 16+
- Maven 3.8+

### 1. Configurar o Banco de Dados

```sql
-- Conectar no PostgreSQL
psql -U postgres

-- Criar banco de dados
CREATE DATABASE jobfitscore;
```

### 2. Configurar application.properties

```properties
spring.application.name=jobfitscore

spring.datasource.url=jdbc:postgresql://localhost:5432/jobfitscore
spring.datasource.username=<SEU USUARIO>
spring.datasource.password=<SUA SENHA>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.repair=true
spring.flyway.repair-on-migrate=true

spring.main.allow-bean-definition-overriding=true

server.address=0.0.0.0
server.port=8080

spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true
```

### 3. Executar a Aplicação

#### Linux/MacOS
```bash
# Clonando o repositório da aplicação
git clone https://github.com/thejaobiell/GS-JOBFIT-SCORE-Java.git
cd GS-Java
# Instalar dependências e executar
./mvnw spring-boot:run
```

#### Windows
```bash
# Clonando o repositório da aplicação
git clone https://github.com/thejaobiell/GS-JOBFIT-SCORE-Java.git
cd GS-Java
# Instalar dependências e executar
.\mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

> **Nota:** Todos os endpoints (exceto `/api/autenticacao/**`) requerem autenticação via Bearer Token no header `Authorization`.

### 🔐 Autenticação

#### Login
Autentica um usuário ou empresa no sistema e retorna os tokens de acesso.

```http
POST /api/autenticacao/login
Content-Type: application/json

{
  "email": "joao.gabriel@jobfitscore.com",
  "senha": "joaogab"
}
```

**Resposta (200 OK):**
```json
{
  "tokenAcesso": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "expiracaoRefreshToken": "2025-11-20T10:30:00"
}
```

#### Atulizar JWT
Autentica um usuário ou empresa no sistema e retorna os tokens de acesso.

```http
POST /api/autenticacao/atualizar-token
Content-Type: application/json

{
  "refreshToken": {{refreshtoken}}
}
```

**Resposta (200 OK):**
```json
{
  "tokenAcesso": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "expiracaoRefreshToken": "2025-11-20T10:30:00"
}
```

---

### 👥 Usuários

#### Listar Usuários (Paginado)
**Permissões:** ADMIN, USUARIO

```http
GET /api/usuarios/listar?page=0&size=10&sortBy=id
Authorization: Bearer {token}
```

**Parâmetros de Query:**
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Itens por página (padrão: 10)
- `sortBy` (opcional): Campo para ordenação (padrão: id)

**Resposta (200 OK):**
```json
{
  "content": [
	{
	  "id": 1,
	  "nome": "João Gabriel Boaventura",
	  "email": "joao.gabriel@jobfitscore.com",
	  "telefone": "(11) 98765-4321",
	  "cpf": "123.456.789-00"
	}
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

#### Buscar Usuário por ID
**Permissões:** ADMIN, USUARIO

```http
GET /api/usuarios/buscar-por-id/{id}
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Gabriel Boaventura",
  "email": "joao.gabriel@jobfitscore.com",
  "telefone": "(11) 98765-4321",
  "cpf": "123.456.789-00"
}
```

#### Cadastrar Usuário
**Permissões:** ADMIN, USUARIO

```http
POST /api/usuarios/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "maria.silva@example.com",
  "senha": "senha123",
  "telefone": "(11) 99999-8888",
  "cpf": "987.654.321-00"
}
```

**Resposta (201 Created):**
```json
{
  "id": 2,
  "nome": "Maria Silva",
  "email": "maria.silva@example.com",
  "telefone": "(11) 99999-8888",
  "cpf": "987.654.321-00"
}
```

#### Atualizar Usuário
**Permissões:** ADMIN, USUARIO

```http
PUT /api/usuarios/atualizar/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Maria Silva Santos",
  "telefone": "(11) 98888-7777"
}
```

**Resposta (200 OK):**
```json
{
  "id": 2,
  "nome": "Maria Silva Santos",
  "email": "maria.silva@example.com",
  "telefone": "(11) 98888-7777",
  "cpf": "987.654.321-00"
}
```

#### Deletar Usuário
**Permissões:** ADMIN, USUARIO

```http
DELETE /api/usuarios/deletar/{id}
Authorization: Bearer {token}
```

**Resposta (204 No Content)**

---

### 🏢 Empresas

#### Listar Empresas
**Permissões:** ADMIN, EMPRESA

```http
GET /api/empresas/listar
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
[
  {
	"id": 1,
	"nomeEmpresa": "XPTO TECH",
	"cnpj": "12.345.678/0001-90",
	"email": "contato@xptotech.com",
	"telefone": "(11) 3333-4444"
  }
]
```

#### Buscar Empresa por ID
**Permissões:** ADMIN, EMPRESA

```http
GET /api/empresas/buscar-por-id/{id}
Authorization: Bearer {token}
```

#### Buscar Empresa por CNPJ
**Permissões:** ADMIN, EMPRESA

```http
GET /api/empresas/buscar-por-cnpj?cnpj=12.345.678/0001-90
Authorization: Bearer {token}
```

#### Atualizar Empresa
**Permissões:** ADMIN, EMPRESA

```http
PUT /api/empresas/atualizar/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nomeEmpresa": "XPTO TECH LTDA",
  "telefone": "(11) 3333-5555"
}
```

#### Deletar Empresa
**Permissões:** ADMIN, EMPRESA

```http
DELETE /api/empresas/deletar/{id}
Authorization: Bearer {token}
```

---

### 💼 Vagas

#### Listar Vagas (Paginado)
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/vagas/listar?page=0&size=10&sortBy=id
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
{
  "content": [
	{
	  "id": 1,
	  "titulo": "Desenvolvedor Java",
	  "descricao": "Desenvolvedor backend com experiência em Spring Boot",
	  "salario": 8000.00,
	  "localizacao": "São Paulo - SP",
	  "empresaId": 1
	}
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

#### Buscar Vaga por ID
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/vagas/buscar-por-id/{id}
Authorization: Bearer {token}
```

#### Cadastrar Vaga
**Permissões:** ADMIN, EMPRESA

```http
POST /api/vagas/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "titulo": "Desenvolvedor Python",
  "descricao": "Desenvolvedor com experiência em Django e Flask",
  "salario": 9000.00,
  "localizacao": "Rio de Janeiro - RJ",
  "empresaId": 1
}
```

**Resposta (201 Created):**
```json
{
  "id": 2,
  "titulo": "Desenvolvedor Python",
  "descricao": "Desenvolvedor com experiência em Django e Flask",
  "salario": 9000.00,
  "localizacao": "Rio de Janeiro - RJ",
  "empresaId": 1
}
```

#### Atualizar Vaga
**Permissões:** ADMIN, EMPRESA

```http
PUT /api/vagas/atualizar/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "titulo": "Desenvolvedor Python Sênior",
  "salario": 12000.00
}
```

#### Deletar Vaga
**Permissões:** ADMIN, EMPRESA

```http
DELETE /api/vagas/deletar/{id}
Authorization: Bearer {token}
```

---

### 🎯 Habilidades

#### Listar Habilidades (Paginado)
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/habilidades/listar?page=0&size=10&sortBy=id
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
{
  "content": [
	{
	  "id": 1,
	  "nome": "Java",
	  "descricao": "Linguagem de programação orientada a objetos"
	},
	{
	  "id": 2,
	  "nome": "Spring Boot",
	  "descricao": "Framework para desenvolvimento Java"
	}
  ],
  "currentPage": 0,
  "totalItems": 2,
  "totalPages": 1
}
```

#### Buscar Habilidade por ID
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/habilidades/buscar-por-id/{id}
Authorization: Bearer {token}
```

#### Cadastrar Habilidade
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
POST /api/habilidades/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Docker",
  "descricao": "Plataforma de containerização"
}
```

**Resposta (201 Created):**
```json
{
  "id": 3,
  "nome": "Docker",
  "descricao": "Plataforma de containerização"
}
```

#### Atualizar Habilidade
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
PUT /api/habilidades/atualizar/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Docker & Kubernetes",
  "descricao": "Containerização e orquestração"
}
```

#### Deletar Habilidade
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
DELETE /api/habilidades/deletar/{id}
Authorization: Bearer {token}
```

---

### 👤📚 Habilidades do Usuário

#### Listar Todas as Habilidades de Usuários
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/usuario-habilidade/listar
Authorization: Bearer {token}
```

#### Buscar por ID
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/usuario-habilidade/buscar-por-id/{id}
Authorization: Bearer {token}
```

#### Buscar Habilidades de um Usuário
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/usuario-habilidade/buscar-por-usuario/{usuarioId}
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
[
  {
	"id": 1,
	"usuarioId": 1,
	"habilidadeId": 1,
	"nivel": "AVANCADO"
  },
  {
	"id": 2,
	"usuarioId": 1,
	"habilidadeId": 2,
	"nivel": "INTERMEDIARIO"
  }
]
```

#### Cadastrar Habilidade para Usuário
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
POST /api/usuario-habilidade/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "usuarioId": 1,
  "habilidadeId": 3,
  "nivel": "BASICO"
}
```

**Resposta (201 Created):**
```json
{
  "id": 3,
  "usuarioId": 1,
  "habilidadeId": 3,
  "nivel": "BASICO"
}
```

#### Deletar Habilidade do Usuário
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
DELETE /api/usuario-habilidade/deletar/{id}
Authorization: Bearer {token}
```

---

### 💼📚 Habilidades da Vaga

#### Cadastrar Habilidade para Vaga
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
POST /api/vaga-habilidade/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "vagaId": 1,
  "habilidadeId": 1,
  "nivelRequerido": "AVANCADO"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "vagaId": 1,
  "habilidadeId": 1,
  "nivelRequerido": "AVANCADO"
}
```

#### Listar Todas as Habilidades de Vagas
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/vaga-habilidade/listar
Authorization: Bearer {token}
```

#### Buscar Habilidades de uma Vaga
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/vaga-habilidade/buscar-por-vaga?vagaId=1
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
[
  {
	"id": 1,
	"vagaId": 1,
	"habilidadeId": 1,
	"nivelRequerido": "AVANCADO"
  },
  {
	"id": 2,
	"vagaId": 1,
	"habilidadeId": 2,
	"nivelRequerido": "INTERMEDIARIO"
  }
]
```

#### Buscar Vagas por Habilidade
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/vaga-habilidade/buscar-por-habilidade?habilidadeId=1
Authorization: Bearer {token}
```

#### Deletar Habilidade da Vaga
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
DELETE /api/vaga-habilidade/deletar/{id}
Authorization: Bearer {token}
```

---

### 📚 Cursos

#### Listar Cursos
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/cursos/listar
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
[
  {
	"id": 1,
	"nome": "Análise e Desenvolvimento de Sistemas",
	"instituicao": "FIAP",
	"dataConclusao": "2024-12-15",
	"usuarioId": 1
  }
]
```

#### Buscar Curso por ID
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/cursos/buscar-por-id/{id}
Authorization: Bearer {token}
```

#### Buscar Cursos de um Usuário
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/cursos/buscar-por-usuario/{usuarioId}
Authorization: Bearer {token}
```

#### Cadastrar Curso
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
POST /api/cursos/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Spring Boot Avançado",
  "instituicao": "Alura",
  "dataConclusao": "2025-06-30",
  "usuarioId": 1
}
```

**Resposta (201 Created):**
```json
{
  "id": 2,
  "nome": "Spring Boot Avançado",
  "instituicao": "Alura",
  "dataConclusao": "2025-06-30",
  "usuarioId": 1
}
```

#### Atualizar Curso
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
PUT /api/cursos/atualizar/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Spring Boot Completo",
  "instituicao": "Alura"
}
```

#### Deletar Curso
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
DELETE /api/cursos/deletar/{id}
Authorization: Bearer {token}
```

---

### 📝 Candidaturas

#### Listar Candidaturas (Paginado)
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/candidaturas/listar?page=0&size=10&sortBy=id
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
{
  "content": [
	{
	  "id": 1,
	  "usuarioId": 1,
	  "vagaId": 1,
	  "dataCandidatura": "2025-11-13T10:30:00",
	  "status": "EM_ANALISE",
	  "score": 85.5
	}
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

#### Buscar Candidatura por ID
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/candidaturas/buscar-por-id/{id}
Authorization: Bearer {token}
```

#### Buscar Candidaturas de um Usuário
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/candidaturas/buscar-por-usuario/{usuarioId}
Authorization: Bearer {token}
```

**Resposta (200 OK):**
```json
[
  {
	"id": 1,
	"usuarioId": 1,
	"vagaId": 1,
	"dataCandidatura": "2025-11-13T10:30:00",
	"status": "EM_ANALISE",
	"score": 85.5
  },
  {
	"id": 2,
	"usuarioId": 1,
	"vagaId": 2,
	"dataCandidatura": "2025-11-13T11:00:00",
	"status": "APROVADO",
	"score": 92.0
  }
]
```

#### Buscar Candidaturas de uma Vaga
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
GET /api/candidaturas/buscar-por-vaga?vagaId=1
Authorization: Bearer {token}
```

#### Cadastrar Candidatura
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
POST /api/candidaturas/cadastrar
Authorization: Bearer {token}
Content-Type: application/json

{
  "usuarioId": 1,
  "vagaId": 2,
  "status": "EM_ANALISE"
}
```

**Resposta (201 Created):**
```json
{
  "id": 3,
  "usuarioId": 1,
  "vagaId": 2,
  "dataCandidatura": "2025-11-13T14:30:00",
  "status": "EM_ANALISE",
  "score": 78.5
}
```

#### Atualizar Candidatura
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
PUT /api/candidaturas/atualizar/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "status": "APROVADO"
}
```

**Resposta (200 OK):**
```json
{
  "id": 3,
  "usuarioId": 1,
  "vagaId": 2,
  "dataCandidatura": "2025-11-13T14:30:00",
  "status": "APROVADO",
  "score": 78.5
}
```

#### Deletar Candidatura
**Permissões:** ADMIN, USUARIO, EMPRESA

```http
DELETE /api/candidaturas/deletar/{id}
Authorization: Bearer {token}
```

**Resposta (204 No Content)**

---

## 👥 Usuários de Teste

### Administrador
- **Email:** `admin@jobfitscore.com`
- **Senha:** `admin`
- **Role:** ADMIN

### Usuários Normais
**João Gabriel**
- **Email:** `joao.gabriel@jobfitscore.com`
- **Senha:** `joaogab`
- **Role:** USUARIO

### Empresas
**XPTO TECH**
- **Email:** `contato@xptotech.com`
- **Senha:** `xptotech`
- **Role:** EMPRESA

---

## 🔒 Segurança

### Autenticação JWT
- Tokens de acesso válidos por **120 minutos**
- Refresh tokens válidos por **7 dias** (10.080 minutos)
- Criptografia de senhas com **BCrypt**
- Chave secreta para assinatura de tokens

### Como Usar o Token
Após o login, inclua o token em todas as requisições:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Hierarquia de Roles
- Implementado com `RoleHierarchy` do Spring Security
- ADMIN herda permissões de USUARIO e EMPRESA
- Proteção de rotas baseada em roles
- Filtro customizado para validação de JWT

---

## 📊 Banco de Dados

### Migrações Flyway

```
db/migration/
├── V1__create_tables.sql   	 # Criação das tabelas
├── V2__insert_table.sql      	 # Inserção de dados
└── V3__insert_table-admin.sql   # Usuário administrador
```

### Diagrama de Relacionamentos

```
usuarios ──┬── usuario_habilidade ──── habilidades
           │
           └── cursos
           │
           └── candidaturas ──── vagas ──┬── empresas
                                         │
                                         └── vaga_habilidade ──── habilidades
```

---

### Usando Postman

1. **Importar a coleção**
   Importe o arquivo [`postman/JobFit-Score.postman_collection.json`](https://github.com/thejaobiell/GS-Java/blob/main/postman/JobFit-Score.postman_collection.json)
2. **Obter o JWT**

   * Abra a pasta `0-JWT` → `Pegar o JWT`.
   * Faça login usando um dos usuários cadastrados.
   * Na resposta, copie o valor do campo `tokenAcesso`.

3. **Salvar o JWT como variável**

   * Selecione o valor de `tokenAcesso` (sem aspas).
   * Clique com o botão direito → **Set as variable**.
   * Clique em `jwt` para setar o valor da variavel com o tokenAcesso.

4. **Use a API**

   * Dependendo de qual conta você utilizou você pode ter acesso restrito a alguns endpoints(como mostrado acima).

---

## 📈 Status Codes

| Código | Descrição |
|--------|-----------|
| 200 | Requisição bem-sucedida |
| 201 | Recurso criado com sucesso |
| 204 | Requisição bem-sucedida sem conteúdo (deleção) |
| 400 | Requisição inválida (dados incorretos) |
| 401 | Não autenticado (token inválido ou ausente) |
| 403 | Sem permissão para acessar o recurso |
| 404 | Recurso não encontrado |
| 500 | Erro interno do servidor |

---

## 🐛 Tratamento de Erros

### Erro de Autenticação
```json
{
  "timestamp": "2025-11-13T14:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido ou expirado",
  "path": "/api/usuarios/listar"
}
```

### Erro de Permissão
```json
{
  "timestamp": "2025-11-13T14:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Você não tem permissão para acessar este recurso",
  "path": "/api/empresas/listar"
}
```

### Erro de Validação
```json
{
  "timestamp": "2025-11-13T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Dados inválidos",
  "errors": [
    {
      "field": "email",
      "message": "Email inválido"
    },
    {
      "field": "senha",
      "message": "Senha deve ter no mínimo 6 caracteres"
    }
  ]
}
```

### Recurso Não Encontrado
```json
{
  "timestamp": "2025-11-13T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuário com ID 999 não encontrado",
  "path": "/api/usuarios/buscar-por-id/999"
}
```

---

### Problemas Comuns

**Erro: "Unable to connect to PostgreSQL"**
- Verifique se o PostgreSQL está rodando
- Confirme as credenciais no `application.properties`
- Teste a conexão: `psql -U postgres -h localhost`

**Erro: "Token inválido ou expirado"**
- Faça login novamente para obter um novo token
- Verifique se está usando o formato correto: `Bearer {token}`

**Erro: "Access Denied"**
- Verifique se você tem a role adequada para o endpoint
- Confirme se o token pertence ao tipo de usuário correto

---

## 👥 Equipe de Desenvolvimento

<table align="center">
<tr>
<td align="center">
<a href="https://github.com/thejaobiell">
<img src="https://github.com/thejaobiell.png" width="100px;" alt="João Gabriel"/><br>
<sub><b>João Gabriel Boaventura</b></sub><br>
<sub>RM554874 • 2TDSB2025</sub><br>
</a>
</td>
<td align="center">
<a href="https://github.com/leomotalima">
<img src="https://github.com/leomotalima.png" width="100px;" alt="Léo Mota"/><br>
<sub><b>Léo Mota Lima</b></sub><br>
<sub>RM557851 • 2TDSB2025</sub><br>
</a>
</td>
<td align="center">
<a href="https://github.com/LucasLDC">
<img src="https://github.com/LucasLDC.png" width="100px;" alt="Lucas Leal"/><br>
<sub><b>Lucas Leal das Chagas</b></sub><br>
<sub>RM551124 • 2TDSB2025</sub><br>
</a>
</td>
</tr>
</table>

---
