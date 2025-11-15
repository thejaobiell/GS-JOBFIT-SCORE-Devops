<div align="center">
  <img src="https://raw.githubusercontent.com/thejaobiell/GS-JOBFIT-SCORE-Java/refs/heads/main/src/main/resources/static/logo.jpeg" alt="JobFit-Score" width="200"/>
  
  # JobFit-Score
  
  [![Java](https://img.shields.io/badge/Java-21-orange.svg?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue.svg?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
  [![Azure](https://img.shields.io/badge/Azure-Container_Instances-0089D6.svg?style=for-the-badge&logo=microsoftazure)](https://azure.microsoft.com/)
  
  [🎥 Ver Demonstração]( ) • 
  [📚 Repositório](https://github.com/thejaobiell/GS-JOBFIT-SCORE-Devops) • 
  [🚀 Deploy](http://jobfitscore-app.brazilsouth.azurecontainer.io:8080/)
  
</div>


---

## 🎯 Sobre o Projeto 

**JobFit-Score** é uma plataforma inovadora que revoluciona o processo de recrutamento através de um **sistema inteligente de pontuação baseado em habilidades**. 

### 🌟 O Problema

O mercado de trabalho enfrenta desafios significativos:
- ❌ Processos de recrutamento longos e ineficientes
- ❌ Dificuldade em avaliar objetivamente a compatibilidade candidato-vaga
- ❌ Candidatos qualificados perdidos em pilhas de currículos
- ❌ Empresas gastando recursos excessivos em triagem manual

### 💡 Nossa Solução

JobFit-Score utiliza **algoritmos inteligentes** para:
- ✅ Calcular automaticamente a compatibilidade entre candidatos e vagas
- ✅ Ranquear candidatos por score de compatibilidade
- ✅ Reduzir tempo de triagem
- ✅ Aumentar precisão na seleção de talentos
- ✅ Facilitar o match perfeito entre habilidades e requisitos

### 🎯 Impacto

- **Para Empresas:** Contratações mais assertivas e processos otimizados
- **Para Candidatos:** Oportunidades alinhadas com suas competências
- **Para o Mercado:** Maior eficiência no ecossistema de recrutamento

---

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
- ✅ Gerenciamento de processos seletivos

### 👨‍💼 Para Administradores
- ✅ Acesso universal a todos os endpoints
- ✅ Gerenciamento completo de usuários e empresas
- ✅ Controle total da plataforma
- ✅ Hierarquia de permissões com Spring Security

---

## 🛠️ Tecnologias Utilizadas

### Backend & Framework
```
Java 21                    Linguagem moderna e robusta
Spring Boot 3.5.7          Framework enterprise
Spring Security 6.5.6      Autenticação e autorização
Spring Data JPA            Camada de persistência
Hibernate                  ORM para mapeamento objeto-relacional
```

### Banco de Dados & Migrações
```
PostgreSQL 16              Banco de dados relacional
Flyway                     Versionamento de schema
JDBC                       Conectividade com banco
```

### Segurança & Autenticação
```
JWT (Auth0)                Tokens stateless
BCrypt                     Hash de senhas
Spring Security            Proteção de endpoints
```

### Ferramentas & Utilitários
```
Maven                      Gerenciamento de dependências
Lombok                     Redução de boilerplate
Bean Validation            Validação de dados
Swagger/OpenAPI            Documentação interativa
```

### DevOps & Deploy
```
Docker                     Containerização
Azure Container Instances  Hospedagem cloud
Azure DevOps               CI/CD pipeline
```

---

## 🏗️ Arquitetura

### 📁 Estrutura do Projeto

```
jobfitscore/
├── 📂 src/main/java/com/gs/fiap/jobfitscore/
│   ├── 📂 controller/              # Endpoints REST
│   ├── 📂 domain/
│   │   ├── autenticacao/           # JWT & Autenticação
│   │   ├── usuario/                # Gestão de usuários
│   │   ├── empresa/                # Gestão de empresas
│   │   ├── vaga/                   # Gestão de vagas
│   │   ├── habilidade/             # Catálogo de skills
│   │   ├── curso/                  # Formações acadêmicas
│   │   ├── candidatura/            # Processo seletivo
│   │   ├── usuariohabilidade/      # Skills dos candidatos
│   │   └── vagahabilidade/         # Requisitos das vagas
│   ├── 📂 infra/
│   │   ├── config/                 # Configurações
│   │   ├── security/               # Spring Security
│   │   ├── swagger/                # Documentação API
│   │   └── exception/              # Tratamento de erros
│   └── 🚀 JobfitscoreApplication   # Entry point
├── 📂 src/main/resources/
│   ├── 📊 db/migration/            # Scripts Flyway
│   ├── 🖼️ static/                  # Recursos estáticos
│   └── ⚙️ application.properties   # Configurações
├── 📂 scripts/         
│       ├── 📝 script-infra.sh      # Script para criação da infraestrutura da aplicação
│       ├── 📝 limpar.sh            # Arquivo de limpeza (usar após uso da aplicação)
│       └── 📝 script-bd.sql        # Arquivo SQL 
└── 📂 dockerfiles/
    └── 🐳 Dockerfile               # Containerização

### 🗄️ Modelo de Dados

```
┌─────────────┐
│  USUARIOS   │
└──────┬──────┘
       │
       ├──────────────────┐
       │                  │
       ▼                  ▼
┌─────────────┐    ┌─────────────┐
│   CURSOS    │    │   USUARIO   │
│             │    │ HABILIDADE  │───────┐
└─────────────┘    └─────────────┘       │
       │                  │              │
       │                  │              ▼
       │                  │       ┌─────────────┐
       │                  │       │ HABILIDADES │
       │                  │       └─────────────┘
       │                  │              ▲
       ▼                  ▼              │
┌─────────────┐    ┌─────────────┐       │
│CANDIDATURAS │    │    VAGA     │       │
└─────────────┘    │ HABILIDADE  │───────┘
       │           └─────────────┘
       │                  │
       │                  ▼
       │           ┌─────────────┐
       └──────────▶│    VAGAS    │
                   └──────┬──────┘
                          │
                          ▼
                   ┌─────────────┐
                   │  EMPRESAS   │
                   └─────────────┘
```

### 🔐 Hierarquia de Permissões

```
                    ┌─────────────┐
                    │    ADMIN    │  ← Acesso Total
                    └──────┬──────┘
                           │
                     Herda permissões
                           │
            ┌──────────────┴─────────────┐
            │                            │ 
     ┌──────▼──────┐             ┌───────▼──────┐
     │   USUARIO   │             │   EMPRESA    │
     └─────────────┘             └──────────────┘
     
     Candidatos                   Recrutadores
     - Perfil próprio             - Vagas próprias
     - Candidaturas               - Candidatos
     - Habilidades                - Processos seletivos
```

---

### ⚙️ Configuração da Aplicação

```properties
spring.application.name=jobfitscore

spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.repair=true
spring.flyway.repair-on-migrate=true

spring.main.allow-bean-definition-overriding=true

server.address=0.0.0.0
server.port=${SERVER_PORT:8080}

```

### 📥 Clone do Projeto

```bash
# Clone o repositório
git clone https://github.com/thejaobiell/GS-JOBFIT-SCORE-Devops.git

# Entre no diretório
cd GS-JOBFIT-SCORE-Devops

# Verifique a estrutura
ls -la
```

### 🚀 Executar da Pipeline

1. Entre na pasta `scripts`
```bash
cd scripts
```
2. Rode o arquivo `script-infra.sh`
```bash
#rode de necessário
chmod +x script-infra.sh

./script-infra.sh
```
> Esse arquivo irá criar o Resource Group, Azure Container Registry(ACR) da aplicação e cria o ACI do Banco de Dados

3. Volte para a raiz do projeto e modifique o arquivo `ativar-pipeline.txt` para a pipeline ativar
```bash
cd ..

nano ativar-pipeline.txt
```
> A duração da execução da pipeline pode durar de 6 a 10 minutos.
>> Acesse o [Azure Devops](https://dev.azure.com/RM554874/GlobalSolution-JobFit-Score) para mais detalhes


#### Conexão com o Banco de dados no VSCode 

Se você usa **VSCode**, instale:
- [Database Client](https://marketplace.visualstudio.com/items?itemName=cweijan.vscode-database-client2)
- [Database Client JDBC](https://marketplace.visualstudio.com/items?itemName=cweijan.dbclient-jdbc)

**Configuração Azure (Deploy):**
```
Connection String:
postgresql://rm554874:JobfitScore2025%23@jobfitscore-db-dns.brazilsouth.azurecontainer.io:5432/jobfitscore
```

---

## 🔌 Uso da API

### 📦 Importar Collection no Postman

1. Baixe a collection: [`JobFit-Score.postman_collection.json`](https://github.com/thejaobiell/GS-JOBFIT-SCORE-Devops/blob/main/postman/JobFit-Score%20Global.postman_collection.json)
2. Abra o Postman
3. **Import** → Arraste o arquivo
4. Configure as variáveis:
   - `{{url}}`: URL do ambiente(utilize essa url `http://jobfitscore-app.brazilsouth.azurecontainer.io:8080/api`)
   - `{{jwt}}`: Token de autenticação (copie o código JWT sem as "aspas" após fazer o LOGIN)
   - `{{refreshtoken}}`: Token de renovação (copie o código REFRESHTOKEN sem as "aspas" após fazer o LOGIN)

### 🔑 Autenticação

### 👥 Usuários de Teste

<table>
<tr>
<th>Tipo</th>
<th>Email</th>
<th>Senha</th>
<th>Role</th>
<th>Acesso</th>
</tr>
<tr>
<td>🔑 <b>Admin</b></td>
<td><code>admin@jobfitscore.com</code></td>
<td><code>admin</code></td>
<td><code>ADMIN</code></td>
<td>✅ Total</td>
</tr>
<tr>
<td>👤 Candidato</td>
<td><code>joao.gabriel@jobfitscore.com</code></td>
<td><code>joaogab</code></td>
<td><code>USUARIO</code></td>
<td>📝 Candidaturas</td>
</tr>
<tr>
<td>🏢 Empresa</td>
<td><code>contato@xptotech.com</code></td>
<td><code>xptotech</code></td>
<td><code>EMPRESA</code></td>
<td>💼 Vagas</td>
</tr>
</table>

#### Obter Token JWT

```http
POST {{url}}/autenticacao/login
Content-Type: application/json

{
  "email": "admin@jobfitscore.com",
  "senha": "admin"
}
```

**Resposta:**
```json
{
  "tokenAcesso": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "expiracaoRefreshToken": "2025-11-22T10:30:00"
}
```

#### Renovar Token

```http
POST {{url}}/autenticacao/atualizar-token
Content-Type: application/json

{
  "refreshToken": "{{refreshtoken}}"
}
```

### 📚 Endpoints Principais

PREENCHA AQUI!

### 📊 Status HTTP

| Código | Significado | Quando Ocorre |
|--------|-------------|---------------|
| `200` | ✅ OK | Requisição bem-sucedida |
| `201` | ✅ Created | Recurso criado com sucesso |
| `204` | ✅ No Content | Deleção bem-sucedida |
| `400` | ❌ Bad Request | Dados inválidos |
| `401` | 🔒 Unauthorized | Token inválido/ausente |
| `403` | 🚫 Forbidden | Sem permissão |
| `404` | 🔍 Not Found | Recurso não encontrado |
| `500` | 💥 Internal Error | Erro no servidor |

### 🐛 Exemplos de Erros

**Autenticação Falhou:**
```json
{
  "timestamp": "2025-11-15T14:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido ou expirado",
  "path": "/api/usuarios/listar"
}
```

**Sem Permissão:**
```json
{
  "timestamp": "2025-11-15T14:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Você não tem permissão para acessar este recurso",
  "path": "/api/empresas/listar"
}
```

**Validação Falhou:**
```json
{
  "timestamp": "2025-11-15T14:30:00",
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

---

## 🔒 Segurança

### 🔐 Autenticação JWT

- **Access Token:** Válido por **120 minutos**
- **Refresh Token:** Válido por **7 dias** (10.080 minutos)
- **Algoritmo:** HS256 (HMAC-SHA256)
- **Criptografia:** BCrypt para senhas

### 🛡️ Proteções Implementadas

```
✅ CSRF Protection
✅ XSS Prevention
✅ SQL Injection Prevention (JPA)
✅ Password Hashing (BCrypt)
✅ JWT Token Validation
✅ HTTPS Ready
```

### 👮 Matriz de Permissões

| Endpoint | ADMIN | USUARIO | EMPRESA |
|----------|:-----:|:-------:|:-------:|
| `/api/usuarios/**` | ✅ | ✅ | ❌ |
| `/api/empresas/**` | ✅ | ❌ | ✅ |
| `/api/vagas/**` | ✅ | ✅ | ✅ |
| `/api/habilidades/**` | ✅ | ✅ | ✅ |
| `/api/cursos/**` | ✅ | ✅ | ✅ |
| `/api/candidaturas/**` | ✅ | ✅ | ✅ |
| `/api/usuario-habilidade/**` | ✅ | ✅ | ✅ |
| `/api/vaga-habilidade/**` | ✅ | ✅ | ✅ |

---

## 🚨 Troubleshooting

### Problemas Comuns

<details>
<summary><b>🔒 Erro: "Token inválido ou expirado"</b></summary>

**Soluções:**
1. Faça login novamente para obter novo token
2. Verifique o formato: `Bearer {token}`
3. Confirme que não passou 2 horas desde o login
4. Use o refresh token se disponível
</details>

<details>
<summary><b>🚫 Erro: "Access Denied"</b></summary>

**Soluções:**
1. Verifique se você tem a role adequada
2. Confirme o token pertence ao tipo correto (USUARIO/EMPRESA/ADMIN)
3. Revise a matriz de permissões
</details>

---

## 👥 Equipe de Desenvolvimento

<div align="center">
  <table>
    <tr>
      <td align="center">
        <a href="https://github.com/thejaobiell">
          <img src="https://github.com/thejaobiell.png" width="120px;" alt="João Gabriel"/><br>
          <sub><b>João Gabriel Boaventura</b></sub><br>
          <sub>RM554874 • 2TDSB2025</sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/leomotalima">
          <img src="https://github.com/leomotalima.png" width="120px;" alt="Léo Mota"/><br>
          <sub><b>Léo Mota Lima</b></sub><br>
          <sub>RM557851 • 2TDSB2025</sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/LucasLDC">
          <img src="https://github.com/LucasLDC.png" width="120px;" alt="Lucas Leal"/><br>
          <sub><b>Lucas Leal das Chagas</b></sub><br>
          <sub>RM551124 • 2TDSB2025</sub>
        </a>
      </td>
    </tr>
  </table>
</div>
