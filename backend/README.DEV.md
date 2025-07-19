# Perfil de Desenvolvimento (dev)

Este projeto agora está configurado com um perfil específico para desenvolvimento que oferece as seguintes funcionalidades:

## Configurações do Perfil Dev

### Segurança
- **Autenticação desabilitada**: Acesso livre a todas as rotas
- **CSRF desabilitado**: Facilita testes de API
- **Console H2 habilitado**: Acesso ao banco de dados em memória

### Banco de Dados
- **H2 em memória**: Banco de dados temporário que é recriado a cada execução
- **Console H2**: Disponível em `http://localhost:8080/h2-console`
  - URL: `jdbc:h2:mem:testdb`
  - Usuário: `sa`
  - Senha: (deixar em branco)

### Logging
- **Debug habilitado** para o pacote da aplicação
- **SQL logging**: Visualização das queries SQL executadas
- **Security logging**: Debug de configurações de segurança

### Desenvolvimento
- **Hot reload**: Reinicialização automática ao detectar mudanças
- **LiveReload**: Atualização automática do browser
- **Cache desabilitado**: Para Thymeleaf e recursos estáticos

## Como usar

### Executar com perfil dev (padrão)
```bash
mvnw spring-boot:run
```

### Executar com perfil específico
```bash
mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Executar com Docker
```bash
docker compose up --build
```

## URLs importantes em desenvolvimento

- **Aplicação**: http://localhost:8080
- **Console H2**: http://localhost:8080/h2-console
- **Actuator** (se habilitado): http://localhost:8080/actuator

## 🎨 Qualidade de Código

O projeto inclui verificações automáticas de qualidade de código:

### Checkstyle
Verificação de estilo de código baseada no Google Java Style Guide.

```bash
# Verificar estilo manualmente (recomendado)
mvnw checkstyle:check

# Gerar relatório completo
mvnw checkstyle:checkstyle

# Executar com verificação automática
mvnw clean compile -Pquality-check

# Usar script auxiliar (recomendado)
# Linux/Mac:
./check-code-quality.sh

# Windows:
check-code-quality.cmd
```

### Relatórios
- **Checkstyle**: `target/site/checkstyle.html`
- **Cobertura de testes**: `target/site/jacoco/index.html`

### Dicas para desenvolvimento
1. Execute verificações antes de commit
2. Configure seu IDE para usar o arquivo `checkstyle.xml`
3. Use o profile `dev` para desenvolvimento local (não falha em violações)
4. Use o profile `ci` para simular ambiente de CI/CD

Para mais detalhes, consulte: [README.CodeQuality.md](README.CodeQuality.md)

## Perfis disponíveis

- **dev**: Desenvolvimento local (padrão)
- **default**: Configuração padrão para outros ambientes

## Observações

- O perfil dev é otimizado para desenvolvimento e **NÃO deve ser usado em produção**
- Todas as configurações de segurança são permissivas para facilitar o desenvolvimento
- O banco de dados é recriado a cada inicialização da aplicação
