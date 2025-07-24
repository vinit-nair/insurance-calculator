@echo off
echo ========================================
echo Stopping UK Life Insurance Calculator
echo ========================================
echo.

echo Stopping containers...
docker-compose down

echo.
echo Application stopped successfully.
echo.
pause 