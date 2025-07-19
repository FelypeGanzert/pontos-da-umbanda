#!/bin/bash

# Script para verificação de qualidade de código
# Uso: ./check-code-quality.sh [--fix] [--skip-style] [--report-only]

set -e

cd "$(dirname "$0")"

echo "🎨 Verificação de Qualidade de Código - Pontos da Umbanda"
echo "=========================================================="

# Parsear argumentos
FIX_MODE=false
SKIP_STYLE=false
REPORT_ONLY=false
PROFILE="dev"

for arg in "$@"; do
    case $arg in
        --fix)
            FIX_MODE=true
            shift
            ;;
        --skip-style)
            SKIP_STYLE=true
            PROFILE="skip-style"
            shift
            ;;
        --report-only)
            REPORT_ONLY=true
            shift
            ;;
        --ci)
            PROFILE="ci"
            shift
            ;;
        *)
            echo "Uso: $0 [--fix] [--skip-style] [--report-only] [--ci]"
            echo "  --fix         Tentar corrigir automaticamente alguns problemas"
            echo "  --skip-style  Pular verificações de estilo"
            echo "  --report-only Gerar apenas relatórios (não falhar)"
            echo "  --ci          Usar configurações de CI (falhar em violações)"
            exit 1
            ;;
    esac
done

echo "🔧 Configuração:"
echo "  Profile: $PROFILE"
echo "  Fix mode: $FIX_MODE"
echo "  Report only: $REPORT_ONLY"
echo ""

# Verificar se Maven wrapper existe
if [ ! -f "./mvnw" ]; then
    echo "❌ Maven wrapper não encontrado. Certifique-se de estar na pasta backend."
    exit 1
fi

# Tornar o Maven wrapper executável
chmod +x mvnw

echo "🏗️  Compilando o projeto..."
./mvnw clean compile -B -q

if [ "$SKIP_STYLE" = false ]; then
    echo ""
    echo "🎨 Executando verificação de estilo (Checkstyle)..."
    
    if [ "$REPORT_ONLY" = true ]; then
        # Apenas gerar relatório
        ./mvnw checkstyle:checkstyle -B -P"$PROFILE" || true
        echo "📊 Relatório Checkstyle gerado em: target/site/checkstyle.html"
    else
        # Verificar e falhar se necessário
        if ./mvnw checkstyle:check -B -P"$PROFILE"; then
            echo "✅ Nenhuma violação de estilo encontrada!"
        else
            echo "⚠️  Violações de estilo encontradas!"
            
            # Gerar relatório detalhado
            ./mvnw checkstyle:checkstyle -B -P"$PROFILE" || true
            
            if [ -f target/checkstyle-result.xml ]; then
                violations=$(grep -c "<error" target/checkstyle-result.xml || echo "0")
                echo "📊 Total de violações: $violations"
                
                if [ "$violations" -gt 0 ]; then
                    echo ""
                    echo "📋 Top 5 tipos de violações:"
                    grep -o 'message="[^"]*"' target/checkstyle-result.xml | \
                        sed 's/message="//g' | sed 's/"//g' | \
                        sort | uniq -c | sort -nr | head -5
                fi
            fi
            
            echo ""
            echo "📁 Relatório detalhado: target/site/checkstyle.html"
            
            if [ "$PROFILE" = "ci" ]; then
                exit 1
            fi
        fi
    fi
else
    echo "⏭️  Pulando verificações de estilo conforme solicitado"
fi

echo ""
echo "🧪 Executando testes..."
./mvnw test -B -q

echo ""
echo "📈 Gerando relatório de cobertura..."
./mvnw jacoco:report -B -q

echo ""
echo "✅ Verificação de qualidade concluída!"
echo ""
echo "📊 Relatórios gerados:"
echo "  - Checkstyle: target/site/checkstyle.html"
echo "  - Cobertura: target/site/jacoco/index.html"
echo "  - Testes: target/surefire-reports/"

if [ "$FIX_MODE" = true ]; then
    echo ""
    echo "🔧 Tentando corrigir problemas automaticamente..."
    
    # Aqui você pode adicionar comandos para correção automática
    # Por exemplo, usando ferramentas como google-java-format
    echo "ℹ️  Correção automática não implementada ainda."
    echo "   Considere usar ferramentas como google-java-format ou IntelliJ para formatação automática."
fi

echo ""
echo "🎯 Próximos passos:"
echo "  1. Revisar relatórios gerados"
echo "  2. Corrigir violações encontradas"
echo "  3. Executar novamente antes do commit"
echo ""
