# Qualidade de Código - Pontos da Umbanda

Este documento descreve as configurações e práticas de qualidade de código implementadas no projeto.

## 🎨 Checkstyle

O projeto utiliza o Checkstyle para garantir a consistência do estilo de código Java.

### Configuração

- **Arquivo de configuração**: `checkstyle.xml`
- **Arquivo de supressões**: `checkstyle-suppressions.xml`
- **Baseado em**: Google Java Style Guide com adaptações para o projeto

### Principais Regras

#### Naming Conventions
- Classes: PascalCase
- Métodos: camelCase
- Variáveis: camelCase
- Constantes: UPPER_SNAKE_CASE
- Pacotes: lowercase com pontos

#### Formatação
- Máximo 150 caracteres por linha (atualizado)
- Indentação com espaços (sem tabs)
- Chaves obrigatórias em blocos
- Espaçamento consistente ao redor de operadores

#### Imports
- Proibido uso de imports com asterisco (*)
- Imports não utilizados são removidos
- Ordem específica de imports

### Executando Localmente

```bash
# Verificar estilo manualmente (recomendado)
./mvnw checkstyle:check

# Gerar relatório (não falha)
./mvnw checkstyle:checkstyle

# Executar com profile que falha em violações
./mvnw clean compile -Pquality-check

# Para CI/CD (automático no pipeline)
./mvnw clean compile -Pci

# Visualizar relatório
# Windows: start target/site/checkstyle.html
# Linux/Mac: open target/site/checkstyle.html
```

### Status Atual do Projeto

**Última verificação**: ✅ **0 violações encontradas!** 🎉

**Configuração atual**:
- ✅ Máximo 150 caracteres por linha
- ✅ Indentação com espaços
- ✅ Imports específicos (sem asterisco)
- ✅ Formatação consistente

🎯 **Status**: **CÓDIGO 100% CONFORME** com as regras do Checkstyle

✨ **Todas as violações foram resolvidas!** O projeto mantém padrão de qualidade consistente.

### Integração no CI/CD

O Checkstyle está integrado no pipeline de CI/CD nos seguintes pontos:

1. **Job de Testes**: Verificação básica de estilo
2. **Job de Qualidade de Código**: Análise detalhada com relatórios

### Supressões

Algumas verificações são suprimidas para casos específicos:

- **Arquivos gerados**: Pasta `target/`
- **Classes de configuração**: Classes com `@Configuration`
- **Entidades JPA**: Campos públicos em entidades
- **Classes de teste**: Magic numbers permitidos
- **DTOs**: Flexibilidade em construtores

### Profiles Maven

O projeto oferece diferentes profiles para diferentes cenários:

- **Padrão**: Compilação normal, Checkstyle apenas manual
- **`dev`**: Configurações flexíveis para desenvolvimento 
- **`ci`**: Checkstyle automático durante compilação (CI/CD)
- **`quality-check`**: Verificação completa de qualidade
- **`skip-style`**: Pula todas as verificações de estilo

```bash
# Desenvolvimento normal (sem Checkstyle automático)
mvnw clean compile

# Com verificação automática de qualidade
mvnw clean compile -Pquality-check

# Para CI/CD (usado no pipeline)
mvnw clean compile -Pci

# Pulando verificações de estilo
mvnw clean compile -Pskip-style
```

#### IntelliJ IDEA

1. Instalar plugin "CheckStyle-IDEA"
2. Configurar o arquivo `checkstyle.xml` do projeto
3. Ativar verificação em tempo real

#### Eclipse

1. Instalar plugin Eclipse Checkstyle
2. Importar configuração do arquivo `checkstyle.xml`
3. Ativar verificação automática

### Boas Práticas

1. **Execute sempre localmente** antes de fazer commit
2. **Corrija violações** em vez de suprimi-las
3. **Use as supressões** apenas quando absolutamente necessário
4. **Mantenha consistência** com o padrão do projeto

### Relatórios

Os relatórios do Checkstyle são gerados em:
- **HTML**: `target/site/checkstyle.html`
- **XML**: `target/checkstyle-result.xml`

No GitHub Actions, os relatórios são disponibilizados como artifacts.

## 📊 Métricas de Qualidade

### Objetivos

- **Zero violações** de Checkstyle em código novo
- **Menos de 10 violações** por arquivo existente
- **100% dos arquivos** seguindo convenções de naming

### Monitoramento

- Dashboard no GitHub Actions
- Relatórios automáticos em cada PR
- Trending de violações ao longo do tempo

## 🔧 Configurações Avançadas

### Personalização

Para ajustar regras específicas, edite o arquivo `checkstyle.xml`:

```xml
<!-- Exemplo: Alterar tamanho máximo de linha -->
<module name="LineLength">
    <property name="max" value="150"/>
</module>
```

### Novas Supressões

Para adicionar supressões, edite `checkstyle-suppressions.xml`:

```xml
<!-- Exemplo: Suprimir regra específica para um arquivo -->
<suppress checks="MagicNumber" files="SpecialCalculator.java"/>
```

## 📚 Recursos Adicionais

- [Documentação Oficial do Checkstyle](https://checkstyle.org/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Plugin Maven Checkstyle](https://maven.apache.org/plugins/maven-checkstyle-plugin/)

---

**Nota**: Esta configuração evolui conforme as necessidades do projeto. Sugestões de melhorias são sempre bem-vindas!
