@echo off
REM Define o diretório do projeto
set PROJECT_DIR=%~dp0

REM Define o diretório de saída para os arquivos .class
set BIN_DIR=%PROJECT_DIR%bin

REM Define o diretório das bibliotecas
set LIB_DIR=%PROJECT_DIR%lib

REM Define o diretório das fontes
set SRC_DIR=%PROJECT_DIR%src

REM Cria o diretório bin se não existir
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

REM Mensagem de Depuração: Verifica se os diretórios estão corretos
echo Projeto: %PROJECT_DIR%
echo Bin: %BIN_DIR%
echo Lib: %LIB_DIR%
echo Src: %SRC_DIR%

REM Compila todos os arquivos .java recursivamente
echo Compilando arquivos Java...
for /r "%SRC_DIR%" %%f in (*.java) do (
    echo Compilando %%f
    javac -d "%BIN_DIR%" -p "%LIB_DIR%;%SRC_DIR%" %%f
)

REM Mensagem de Depuração: Verifica se a compilação foi bem-sucedida
if errorlevel 1 (
    echo Erro na compilacao
    pause
    exit /b 1
)

REM Executa a classe principal com o nome do módulo correto
echo Executando a classe principal...
java -p "%BIN_DIR%;%LIB_DIR%" -m BetterBetTest/app.App

REM Mensagem de Depuração: Verifica se a execução foi bem-sucedida
if errorlevel 1 (
    echo Erro na execucao
    pause
    exit /b 1
)

pause
