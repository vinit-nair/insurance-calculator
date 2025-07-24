@echo off
echo ========================================
echo UK Life Insurance Calculator
echo ========================================
echo.

REM Check if Docker is installed
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Docker is not installed or not running!
    echo.
    echo Please install Docker Desktop from: https://www.docker.com/products/docker-desktop/
    echo After installation, make sure Docker Desktop is running and try again.
    echo.
    pause
    exit /b 1
)

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Docker is not running!
    echo.
    echo Please start Docker Desktop and try again.
    echo.
    pause
    exit /b 1
)

echo Docker is available and running.
echo.

REM Check if port 8080 is available
netstat -an | findstr ":8080" >nul 2>&1
if %errorlevel% equ 0 (
    echo WARNING: Port 8080 is already in use!
    echo.
    echo This might be another instance of the application or a different service.
    echo The application will try to start anyway, but you might see an error.
    echo.
    pause
)

echo Starting UK Life Insurance Calculator...
echo.
echo This will:
echo - Build the Docker image (first time only)
echo - Start the application
echo - Make it available at http://localhost:8080
echo.
echo Press Ctrl+C to stop the application
echo.

REM Build and start the application
docker-compose up --build

echo.
echo Application stopped.
pause 