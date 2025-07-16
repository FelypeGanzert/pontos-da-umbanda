#!/bin/bash

# 🚀 Script de CI Local - Pontos da Umbanda
# Execute este script para simular o pipeline CI localmente

echo "🚀 Iniciando pipeline CI local..."

cd backend

echo "📦 Verificando dependências do Maven..."
chmod +x ./mvnw

echo "🔧 Compilando aplicação..."
./mvnw clean compile -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Falha na compilação!"
    exit 1
fi

echo "🧪 Executando testes unitários..."
./mvnw test
if [ $? -ne 0 ]; then
    echo "❌ Falha nos testes!"
    exit 1
fi

echo "📊 Gerando relatório de cobertura..."
./mvnw jacoco:report
if [ $? -ne 0 ]; then
    echo "⚠️ Falha na geração do relatório de cobertura"
fi

echo "🔒 Executando análise de segurança..."
./mvnw org.owasp:dependency-check-maven:check
if [ $? -ne 0 ]; then
    echo "⚠️ Vulnerabilidades encontradas!"
fi

echo "📦 Gerando build final..."
./mvnw package -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Falha no build!"
    exit 1
fi

echo "🐳 Testando build Docker..."
docker build -t pontos-umbanda-local:latest .
if [ $? -ne 0 ]; then
    echo "❌ Falha no build Docker!"
    exit 1
fi

echo "✅ Pipeline CI executado com sucesso!"
echo "📊 Relatórios disponíveis em:"
echo "   - Testes: target/surefire-reports/"
echo "   - Cobertura: target/site/jacoco/"
echo "   - Segurança: target/dependency-check-report.html"
echo "   - JAR: target/*.jar"
