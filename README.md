# 🕯️ Pontos da Umbanda - Backend

[![CI/CD Pipeline](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/ci-cd.yml)
[![Deploy](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/deploy.yml/badge.svg)](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/deploy.yml)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 **Sobre o Projeto**

Sistema backend para catalogar e gerenciar pontos cantados da Umbanda, desenvolvido com Spring Boot 3.x e Java 17. Este projeto visa preservar e organizar digitalmente os pontos sagrados da tradição umbandista.

## 🚀 **Tecnologias Utilizadas**

### **Backend Stack**
- **Java 17** (LTS) - Linguagem principal
- **Spring Boot 3.5.3** - Framework principal
- **Spring Security** - Segurança e autenticação
- **Spring Data JPA** - Persistência de dados
- **Spring Boot Actuator** - Monitoramento e métricas
- **H2 Database** - Banco em memória (desenvolvimento)
- **SpringDoc OpenAPI** - Documentação automática da API

### **Build & DevOps**
- **Maven 3.9+** - Gerenciamento de dependências
- **Docker** - Containerização
- **GitHub Actions** - CI/CD Pipeline
- **JaCoCo** - Cobertura de código
- **OWASP Dependency Check** - Análise de segurança

### **Qualidade & Testes**
- **JUnit 5** - Testes unitários
- **Spring Boot Test** - Testes de integração
- **Artillery** - Testes de performance
- **Maven Surefire** - Execução de testes

## 🛠️ **Pré-requisitos**

- **Java 17+** ([Download](https://adoptium.net/temurin/releases/?version=17))
- **Maven 3.9+** ou usar o wrapper incluído
- **Docker** (opcional, para containerização)
- **Git** para controle de versão

## 🚀 **Instalação e Execução**

### **1. Clonar o Repositório**
```bash
git clone https://github.com/FelypeGanzert/pontos-da-umbanda.git
cd pontos-da-umbanda/backend
```

### **2. Executar Localmente**
```bash
# Usando Maven Wrapper (recomendado)
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run

# Executar em background
nohup ./mvnw spring-boot:run &
```

### **3. Executar com Docker**
```bash
# Build da imagem
docker build -t pontos-umbanda .

# Executar container
docker run -p 8080:8080 pontos-umbanda

# Usando Docker Compose
docker compose up -d
```

## 🌐 **Endpoints da Aplicação**

### **URLs Principais**
- **API Base**: http://localhost:8080
- **Documentação**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
- **Health Check**: http://localhost:8080/actuator/health
- **Métricas**: http://localhost:8080/actuator/metrics
- **Info**: http://localhost:8080/actuator/info

### **Configuração H2 Database**
- **URL**: `jdbc:h2:mem:testdb`
- **Usuário**: `sa`
- **Senha**: _(deixar em branco)_

## 🧪 **Testes e Qualidade**

### **Executar Testes**
```bash
# Todos os testes
./mvnw test

# Testes com relatório de cobertura
./mvnw test jacoco:report

# Verificação de segurança
./mvnw org.owasp:dependency-check-maven:check

# Build completo com testes
./mvnw clean package
```

### **Métricas de Qualidade**
- **Cobertura de Código**: Mínimo 50% (JaCoCo)
- **Análise de Segurança**: OWASP Dependency Check
- **Testes Automatizados**: JUnit 5 + Spring Boot Test
- **CI/CD**: Pipeline automatizado no GitHub Actions

## 📊 **Pipeline CI/CD**

O projeto possui pipeline automatizado que executa em cada push:

### **🧪 Etapa: Tests & Quality**
- ✅ Compilação do código
- ✅ Execução de testes unitários
- ✅ Geração de relatórios de teste
- ✅ Análise de cobertura de código
- ✅ Upload de relatórios

### **🔐 Etapa: Security Scan**
- ✅ OWASP Dependency Check
- ✅ Análise de vulnerabilidades
- ✅ Relatório de segurança

### **🐳 Etapa: Build & Test**
- ✅ Build da aplicação
- ✅ Criação de imagem Docker
- ✅ Teste do container
- ✅ Health check automático

### **⚡ Etapa: Performance Tests** _(apenas PRs)_
- ✅ Testes de carga com Artillery
- ✅ Verificação de performance
- ✅ Relatórios de resposta

## 📁 **Estrutura do Projeto**

```
pontos-da-umbanda/
├── backend/                    # Aplicação Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Código fonte
│   │   │   │   └── com/felypeganzert/backend/
│   │   │   │       ├── BackendApplication.java
│   │   │   │       └── config/
│   │   │   │           ├── DevSecurityConfig.java
│   │   │   │           └── SecurityConfig.java
│   │   │   └── resources/     # Configurações
│   │   │       ├── application.properties
│   │   │       └── application-dev.properties
│   │   └── test/              # Testes
│   │       └── java/
│   ├── target/                # Artefatos gerados
│   ├── .mvn/                  # Maven wrapper
│   ├── Dockerfile             # Container configuration
│   ├── docker-compose.yml     # Multi-container setup
│   ├── mvnw & mvnw.cmd       # Maven wrappers
│   └── pom.xml               # Dependências Maven
├── .github/workflows/         # GitHub Actions
│   ├── ci-cd.yml             # Pipeline principal
│   └── deploy.yml            # Pipeline de deploy
├── .gitignore                # Exclusões Git
└── README.md                 # Este arquivo
```

## ⚙️ **Configuração**

### **Perfis Disponíveis**
- **`dev`** - Desenvolvimento local (padrão)
  - H2 em memória
  - Console H2 habilitado
  - Hot reload ativo
  - Logs detalhados
  - Sem autenticação

- **`prod`** - Produção
  - Banco de dados externo
  - Segurança habilitada
  - Logs otimizados

### **Variáveis de Ambiente**
```bash
# Perfil ativo
SPRING_PROFILES_ACTIVE=dev

# Configurações JVM
JAVA_OPTS=-Xmx512m -Xms256m

# Configurações específicas
SERVER_PORT=8080
```

### **Propriedades Importantes**
```properties
# Aplicação
spring.application.name=backend
spring.profiles.active=dev

# Banco H2 (dev)
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Actuator
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

## 🤝 **Contribuindo**

### **Fluxo de Contribuição**
1. **Fork** o projeto
2. **Clone** seu fork
3. **Crie uma branch** para sua feature
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```
4. **Desenvolva** sua funcionalidade
5. **Execute os testes**
   ```bash
   ./mvnw test
   ```
6. **Commit** suas mudanças
   ```bash
   git commit -m "feat: adiciona nova funcionalidade"
   ```
7. **Push** para sua branch
   ```bash
   git push origin feature/nova-funcionalidade
   ```
8. **Abra um Pull Request**

### **Padrões de Commit**
- `feat:` - Nova funcionalidade
- `fix:` - Correção de bug
- `docs:` - Documentação
- `style:` - Formatação
- `refactor:` - Refatoração
- `test:` - Testes
- `chore:` - Manutenção

## 📈 **Roadmap**

### **Versão 1.0** _(Em Desenvolvimento)_
- [ ] API REST para pontos
- [ ] Autenticação JWT
- [ ] Categorização por linha
- [ ] Busca avançada

### **Versão 2.0** _(Planejado)_
- [ ] Frontend web
- [ ] Upload de áudios
- [ ] Integração com banco PostgreSQL
- [ ] Deploy em cloud

### **Versão 3.0** _(Futuro)_
- [ ] Aplicativo mobile
- [ ] Reconhecimento de áudio
- [ ] Comunidade de usuários

## 📄 **Licença**

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 🙏 **Agradecimentos**

- **Comunidade Umbandista** - Pela preservação dos ensinamentos
- **Spring Community** - Pelo framework excepcional
- **Contributors** - Por todas as contribuições

---

## 📞 **Contato**

- **Desenvolvedor**: Felype Ganzert
- **GitHub**: [@FelypeGanzert](https://github.com/FelypeGanzert)
- **Projeto**: [pontos-da-umbanda](https://github.com/FelypeGanzert/pontos-da-umbanda)

---

**Desenvolvido com ❤️ e respeito pelos ensinamentos da Umbanda**

**Axé! 🕯️**
