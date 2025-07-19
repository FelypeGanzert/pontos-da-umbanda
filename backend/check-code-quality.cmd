@echo off
setlocal enabledelayedexpansion

REM Script para verificação de qualidade de código
REM Uso: check-code-quality.cmd [--fix] [--skip-style] [--report-only] [--ci]

cd /d "%~dp0"

echo.
echo 🎨 Verificação de Qualidade de Código - Pontos da Umbanda
echo ==========================================================

REM Parsear argumentos
set FIX_MODE=false
set SKIP_STYLE=false
set REPORT_ONLY=false
set PROFILE=dev

:parse_args
if "%~1"=="--fix" (
    set FIX_MODE=true
    shift
    goto parse_args
)
if "%~1"=="--skip-style" (
    set SKIP_STYLE=true
    set PROFILE=skip-style
    shift
    goto parse_args
)
if "%~1"=="--report-only" (
    set REPORT_ONLY=true
    shift
    goto parse_args
)
if "%~1"=="--ci" (
    set PROFILE=ci
    shift
    goto parse_args
)
if "%~1"=="--help" (
    echo Uso: %0 [--fix] [--skip-style] [--report-only] [--ci]
    echo   --fix         Tentar corrigir automaticamente alguns problemas
    echo   --skip-style  Pular verificações de estilo
    echo   --report-only Gerar apenas relatórios (não falhar)
    echo   --ci          Usar configurações de CI (falhar em violações)
    exit /b 0
)
if not "%~1"=="" (
    echo Argumento inválido: %~1
    echo Use --help para ver as opções disponíveis
    exit /b 1
)

echo 🔧 Configuração:
echo   Profile: %PROFILE%
echo   Fix mode: %FIX_MODE%
echo   Report only: %REPORT_ONLY%
echo.

REM Verificar se Maven wrapper existe
if not exist "mvnw.cmd" (
    echo ❌ Maven wrapper não encontrado. Certifique-se de estar na pasta backend.
    exit /b 1
)

echo 🏗️  Compilando o projeto...
call mvnw.cmd clean compile -B -q
if errorlevel 1 (
    echo ❌ Falha na compilação
    exit /b 1
)

if "%SKIP_STYLE%"=="false" (
    echo.
    echo 🎨 Executando verificação de estilo (Checkstyle)...
    
    if "%REPORT_ONLY%"=="true" (
        REM Apenas gerar relatório
        call mvnw.cmd checkstyle:checkstyle -B -P%PROFILE%
        echo 📊 Relatório Checkstyle gerado em: target\site\checkstyle.html
    ) else (
        REM Verificar e falhar se necessário
        call mvnw.cmd checkstyle:check -B -P%PROFILE%
        if errorlevel 1 (
            echo ⚠️  Violações de estilo encontradas!
            
            REM Gerar relatório detalhado
            call mvnw.cmd checkstyle:checkstyle -B -P%PROFILE%
            
            if exist target\checkstyle-result.xml (
                for /f %%i in ('findstr /c:"<error" target\checkstyle-result.xml ^| find /c /v ""') do set violations=%%i
                echo 📊 Total de violações: !violations!
            )
            
            echo.
            echo 📁 Relatório detalhado: target\site\checkstyle.html
            
            if "%PROFILE%"=="ci" (
                exit /b 1
            )
        ) else (
            echo ✅ Nenhuma violação de estilo encontrada!
        )
    )
) else (
    echo ⏭️  Pulando verificações de estilo conforme solicitado
)

echo.
echo 🧪 Executando testes...
call mvnw.cmd test -B -q
if errorlevel 1 (
    echo ❌ Falha nos testes
    exit /b 1
)

echo.
echo 📈 Gerando relatório de cobertura...
call mvnw.cmd jacoco:report -B -q

echo.
echo ✅ Verificação de qualidade concluída!
echo.
echo 📊 Relatórios gerados:
echo   - Checkstyle: target\site\checkstyle.html
echo   - Cobertura: target\site\jacoco\index.html
echo   - Testes: target\surefire-reports\

if "%FIX_MODE%"=="true" (
    echo.
    echo 🔧 Tentando corrigir problemas automaticamente...
    echo ℹ️  Correção automática não implementada ainda.
    echo    Considere usar ferramentas como google-java-format ou IntelliJ para formatação automática.
)

echo.
echo 🎯 Próximos passos:
echo   1. Revisar relatórios gerados
echo   2. Corrigir violações encontradas
echo   3. Executar novamente antes do commit
echo.
