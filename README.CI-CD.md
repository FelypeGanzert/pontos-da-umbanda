# 🚀 Pipeline CI/CD - Pontos da Umbanda

Este documento descreve o pipeline de CI/CD configurado para o projeto **Pontos da Umbanda**.

## 📋 **Visão Geral**

O pipeline é executado automaticamente em cada push/pull request e inclui múltiplas etapas de qualidade, segurança e deploy.

## 🔧 **Workflows Configurados**

### 1. **CI/CD Pipeline** (`ci-cd.yml`)
Executado em: `push` para branches `main`, `master`, `develop` e `pull_request`

#### Etapas do Pipeline:

##### 🧪 **Tests & Code Quality**
- ✅ Checkout do código
- ☕ Setup do JDK 17 (Eclipse Temurin)
- 📦 Cache das dependências Maven
- 🔧 Compilação da aplicação
- 🧪 Execução dos testes unitários
- 📊 Geração de relatórios de teste
- 📈 Análise de cobertura de código (JaCoCo)
- 📤 Upload dos resultados para Codecov

##### 🏗️ **Build Application**
- 🔧 Build completo da aplicação
- 📦 Upload dos artefatos JAR
- ⏱️ Retenção de 7 dias

##### 🔐 **Security Scan**
- 🛡️ OWASP Dependency Check
- 📤 Upload dos relatórios de segurança
- ⚠️ Falha em vulnerabilidades CVSS >= 7

##### 🐳 **Docker Build**
- 🏗️ Build da imagem Docker
- 🔍 Teste básico da imagem
- 💾 Cache otimizado para GitHub Actions

##### ⚡ **Performance Tests** (apenas PRs)
- 🚀 Start da aplicação
- ⚡ Testes de performance com Artillery
- 📊 Relatórios de performance

##### 📢 **Notifications**
- ✅ Notificação de sucesso
- ❌ Notificação de falha

### 2. **Deploy Pipeline** (`deploy.yml`)
Executado em: `release` publicado ou trigger manual

#### Recursos:
- 🚀 Deploy automático para staging/production
- 🐳 Build e push para GitHub Container Registry
- 🏷️ Versionamento automático com tags semver
- 🔍 Health checks pós-deploy
- 🔐 Autenticação segura com GitHub Token

## 🛠️ **Ferramentas e Tecnologias**

### **CI/CD Stack:**
- **GitHub Actions** - Orquestração
- **Maven** - Build e gerenciamento de dependências
- **JaCoCo** - Cobertura de código
- **OWASP Dependency Check** - Análise de segurança
- **Docker** - Containerização
- **Artillery** - Testes de performance

### **Qualidade de Código:**
- **JUnit 5** - Testes unitários
- **Spring Boot Test** - Testes de integração
- **Codecov** - Relatórios de cobertura
- **Test Reporter** - Relatórios visuais

### **Segurança:**
- **OWASP** - Análise de vulnerabilidades
- **GitHub Security** - Scanning automático
- **Dependabot** - Atualizações de segurança

## 📊 **Métricas e Monitoramento**

### **Endpoints de Monitoramento:**
```bash
# Health Check
GET /actuator/health

# Métricas da aplicação
GET /actuator/metrics

# Informações da aplicação
GET /actuator/info

# Todos os endpoints (apenas dev)
GET /actuator
```

### **Cobertura de Código:**
- **Mínimo exigido:** 50% de cobertura de linha
- **Relatórios:** Gerados automaticamente em cada build
- **Integração:** Codecov para visualização

## 🚀 **Como Usar**

### **Desenvolvimento Local:**
```bash
# Executar testes
./mvnw test

# Gerar relatório de cobertura
./mvnw jacoco:report

# Verificar segurança
./mvnw org.owasp:dependency-check-maven:check

# Build completo
./mvnw clean package
```

### **Deploy Manual:**
```bash
# Trigger deploy para staging
gh workflow run deploy.yml -f environment=staging

# Trigger deploy para production
gh workflow run deploy.yml -f environment=production
```

## 📈 **Badges do Projeto**

Adicione estes badges ao README principal:

```markdown
![CI/CD](https://github.com/seu-usuario/pontos-da-umbanda/workflows/CI/CD%20Pipeline/badge.svg)
![Deploy](https://github.com/seu-usuario/pontos-da-umbanda/workflows/Deploy%20to%20Production/badge.svg)
[![codecov](https://codecov.io/gh/seu-usuario/pontos-da-umbanda/branch/main/graph/badge.svg)](https://codecov.io/gh/seu-usuario/pontos-da-umbanda)
```

## 🔧 **Configurações Necessárias**

### **Secrets do GitHub:**
- `GITHUB_TOKEN` - Automático
- `CODECOV_TOKEN` - Para upload de cobertura
- `DEPLOY_KEY` - Para deploy em servidores (se aplicável)

### **Environments:**
- `staging` - Ambiente de homologação
- `production` - Ambiente de produção

## 🎯 **Próximos Passos**

1. **Integração com SonarQube** para análise de código
2. **Testes E2E** com Selenium/Playwright
3. **Deploy automático** para Kubernetes/AWS
4. **Monitoring** com Prometheus/Grafana
5. **Alertas** via Slack/Discord
6. **Database migrations** automáticas

## 📚 **Referências**

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [OWASP Dependency Check](https://owasp.org/www-project-dependency-check/)
