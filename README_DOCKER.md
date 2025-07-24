# Docker Setup - UK Life Insurance Calculator

This project uses Docker to run the UK Life Insurance Calculator without requiring any local dependencies.

## Prerequisites

- **Docker Desktop** - [Download here](https://www.docker.com/products/docker-desktop/)

## Quick Start

### Windows Users
```bash
# Start the application
run-app.bat

# Stop the application
stop-app.bat

# Clean up Docker resources (optional)
cleanup.bat
```

### Mac/Linux Users
```bash
# Make scripts executable (first time only)
chmod +x run-app.sh stop-app.sh cleanup.sh

# Start the application
./run-app.sh

# Stop the application
./stop-app.sh

# Clean up Docker resources (optional)
./cleanup.sh
```

### Manual Commands
```bash
# Start the application
docker-compose up --build

# Stop the application
docker-compose down

# Clean up everything
docker-compose down -v
docker system prune -f
```

## Access the Application

Once running, open your browser and go to:
- **Main Application**: http://localhost:8080
- **API Documentation**: http://localhost:8080/lloyds-insurance-calculator/swagger-ui.html
- **Health Check**: http://localhost:8080/lloyds-insurance-calculator/actuator/health

## What's Included

### Frontend (React + TypeScript)
- Node.js 18
- React 18
- TypeScript
- All npm dependencies

### Backend (Spring Boot + Java)
- Java 17
- Maven
- Spring Boot 3.2.0
- UK-compliant insurance calculations

### Production Runtime
- Alpine Linux (minimal)
- Non-root user execution
- Security headers
- Health checks
- UK timezone configuration

## Troubleshooting

### Docker is not running
1. Start Docker Desktop
2. Wait for it to fully load (whale icon in system tray)
3. Try running the application again

### Port 8080 is already in use
- The script will warn you about this
- Stop other applications using port 8080 or let Docker try to start anyway

### Build fails
- Check your internet connection (needs to download dependencies)
- Ensure Docker has enough memory (4GB+ recommended)
- Try running `docker system prune` to free up space

### Application won't start
- Check Docker Desktop is running
- Try restarting Docker Desktop
- Run `docker-compose logs` to see error messages

## System Requirements

- **RAM**: 4GB (8GB recommended)
- **Disk Space**: 10GB free space
- **OS**: Windows 10/11, macOS 10.15+, or Linux

## Security Features

- ✅ Non-root user execution
- ✅ Minimal base images
- ✅ Security headers
- ✅ Rate limiting
- ✅ Health checks
- ✅ No sensitive data in images

---

**That's it!** No Java, Node.js, Maven, or any other dependencies needed. Everything runs in containers! 🐳 