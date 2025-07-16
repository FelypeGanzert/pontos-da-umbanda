# 🕯️ Pontos da Umbanda - Backend

[![CI/CD Pipeline](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/ci-cd.yml)
[![Simple Test](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/simple-test.yml/badge.svg)](https://github.com/FelypeGanzert/pontos-da-umbanda/actions/workflows/simple-test.yml)

## 📋 **Sobre o Projeto**

Sistema backend para catalogar e gerenciar pontos cantados da Umbanda, desenvolvido com Spring Boot 3.x e Java 17.

## 🚀 **Tecnologias**

- **Java 17** (LTS)
- **Spring Boot 3.5.3**
- **Spring Security** 
- **Spring Data JPA**
- **H2 Database** (desenvolvimento)
- **Maven** (build tool)
- **Docker** (containerização)
- **GitHub Actions** (CI/CD)

## 🛠️ **Desenvolvimento**

### **Requisitos**
- Java 17+
- Maven 3.6+
- Docker (opcional)

### **Executar Localmente**
```bash
# Clonar repositório
git clone https://github.com/FelypeGanzert/pontos-da-umbanda.git
cd pontos-da-umbanda/backend

# Executar aplicação (perfil dev)
./mvnw spring-boot:run

# Executar testes
./mvnw test

# Build completo
./mvnw clean package
```

### **Acessar Aplicação**
- **API**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **Health Check**: http://localhost:8080/actuator/health
- **Métricas**: http://localhost:8080/actuator/metrics

## 🐳 **Docker**

```bash
# Build da imagem
docker build -t pontos-umbanda .

# Executar container
docker run -p 8080:8080 pontos-umbanda

# Usando Docker Compose
docker compose up
```

## 🧪 **Testes e Qualidade**

- **Cobertura de Código**: JaCoCo (mínimo 50%)
- **Análise de Segurança**: OWASP Dependency Check
- **Testes Unitários**: JUnit 5
- **Testes de Integração**: Spring Boot Test

## 📊 **CI/CD Pipeline**

O projeto possui pipeline automatizado que executa:

✅ **Compilação** e validação de código  
✅ **Testes unitários** com relatórios  
✅ **Análise de cobertura** de código  
✅ **Verificação de segurança** (OWASP)  
✅ **Build Docker** e testes de container  
✅ **Testes de performance** (em PRs)  

## 📁 **Estrutura do Projeto**

```
backend/
├── src/
│   ├── main/
│   │   ├── java/          # Código fonte
│   │   └── resources/     # Configurações
│   └── test/              # Testes
├── .github/workflows/     # GitHub Actions
├── Dockerfile            # Container configuration
├── docker-compose.yml    # Multi-container setup
└── pom.xml              # Dependencies
```

## 🔧 **Configuração**

### **Perfis Disponíveis**
- **dev**: Desenvolvimento local (padrão)
- **prod**: Produção

### **Variáveis de Ambiente**
```bash
SPRING_PROFILES_ACTIVE=dev
JAVA_OPTS=-Xmx512m
```

## 🤝 **Contribuindo**

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 **Licença**

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

## 🙏 **Agradecimentos**

Projeto desenvolvido com respeito e reverência aos ensinamentos da Umbanda.

---

**Axé! 🕯️**
