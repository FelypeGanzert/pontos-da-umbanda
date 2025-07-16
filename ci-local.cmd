@echo off
REM 🚀 Script de CI Local - Pontos da Umbanda (Windows)
REM Execute este script para simular o pipeline CI localmente

echo 🚀 Iniciando pipeline CI local...

cd backend

echo 📦 Verificando dependências do Maven...

echo 🔧 Compilando aplicação...
call mvnw.cmd clean compile -DskipTests
if %ERRORLEVEL% neq 0 (
    echo ❌ Falha na compilação!
    exit /b 1
)

echo 🧪 Executando testes unitários...
call mvnw.cmd test
if %ERRORLEVEL% neq 0 (
    echo ❌ Falha nos testes!
    exit /b 1
)

echo 📊 Gerando relatório de cobertura...
call mvnw.cmd jacoco:report
if %ERRORLEVEL% neq 0 (
    echo ⚠️ Falha na geração do relatório de cobertura
)

echo 🔒 Executando análise de segurança...
call mvnw.cmd org.owasp:dependency-check-maven:check
if %ERRORLEVEL% neq 0 (
    echo ⚠️ Vulnerabilidades encontradas!
)

echo 📦 Gerando build final...
call mvnw.cmd package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo ❌ Falha no build!
    exit /b 1
)

echo 🐳 Testando build Docker...
docker build -t pontos-umbanda-local:latest .
if %ERRORLEVEL% neq 0 (
    echo ❌ Falha no build Docker!
    exit /b 1
)

echo ✅ Pipeline CI executado com sucesso!
echo 📊 Relatórios disponíveis em:
echo    - Testes: target\surefire-reports\
echo    - Cobertura: target\site\jacoco\
echo    - Segurança: target\dependency-check-report.html
echo    - JAR: target\*.jar

pause
