@echo off
echo Starting Lloyds Insurance Calculator...
echo.
echo Building and starting Frontend and Backend services...
echo This may take a few moments...
echo.

REM Check if Node.js is installed
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Node.js is not installed or not in PATH
    echo Please install Node.js from https://nodejs.org/
    pause
    exit /b 1
)

REM Install frontend dependencies if needed
echo Installing frontend dependencies...
cd src\main\frontend
if not exist node_modules (
    echo Running npm install...
    npm install
    if %errorlevel% neq 0 (
        echo Error: npm install failed
        pause
        exit /b 1
    )
)

REM Start the React development server
echo Starting Frontend (React) on port 3000...
start "Frontend - React Dev Server" cmd /k "npm start"

REM Go back to root directory
cd ..\..\..

REM Start the Spring Boot application
echo Starting Backend (Spring Boot) on port 8080...
start "Backend - Spring Boot" cmd /k "mvn spring-boot:run"

REM Wait for both services to start
echo.
echo Waiting for services to start...
echo Frontend: http://localhost:3000
echo Backend API: http://localhost:8080
echo.
timeout /t 20 /nobreak >nul

REM Open the browser to React dev server
echo Opening browser...
start http://localhost:3000

echo.
echo ==========================================
echo Application started successfully!
echo ==========================================
echo Frontend URL: http://localhost:3000
echo Backend API: http://localhost:8080
echo Backend Swagger: http://localhost:8080/swagger-ui.html
echo.
echo Both services are running in separate windows.
echo Close those windows to stop the services.
echo.
echo Press any key to exit this window...
pause >nul 