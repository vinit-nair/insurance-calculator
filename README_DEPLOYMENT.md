# UK Life Insurance Calculator - Deployment Guide

## Overview
This is a full-stack application with:
- **Backend**: Java Spring Boot (Port 8080)
- **Frontend**: React TypeScript (Built into Spring Boot static resources)
- **Security**: Comprehensive security headers and validation
- **Data Protection**: No data persistence, client-side only processing

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm (automatically managed by frontend-maven-plugin)

## Build & Run

### Option 1: Full Build (Recommended)
```bash
# Build both frontend and backend
mvn clean install

# Run the application
java -jar target/insurance-calculator-1.0.0.jar
```

### Option 2: Development Mode
```bash
# Terminal 1: Start Spring Boot backend
mvn spring-boot:run

# Terminal 2: Start React frontend (development server)
cd src/main/frontend
npm start
```

## Application URLs
- **Production**: http://localhost:8080
- **Development Frontend**: http://localhost:3000 (with proxy to :8080)
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/insurance/health

## Security Features
- **HTTPS Ready**: Configure SSL in application.yml for production
- **Security Headers**: X-Frame-Options, X-XSS-Protection, CSP, etc.
- **Input Validation**: Comprehensive validation on all API endpoints
- **No Data Persistence**: All calculations are stateless
- **CORS Protection**: Configured for specific origins only

## API Endpoints
- `POST /api/insurance/calculate` - Calculate insurance needs
- `GET /api/insurance/health` - Service health check

## Environment Configuration
Configure in `src/main/resources/application.yml`:
```yaml
server:
  port: 8080
  # For production, add SSL configuration
logging:
  level:
    com.lloyds.insurance: INFO
```

## Production Deployment
1. Build the application: `mvn clean install`
2. Copy `target/insurance-calculator-1.0.0.jar` to production server
3. Configure environment variables for production settings
4. Run with: `java -jar insurance-calculator-1.0.0.jar`

## Features Implemented
✅ **All Original Functionality Preserved**
- Interactive form with real-time validation
- Comprehensive tooltips with UK insurance explanations
- Multiple calculation methods (Income Replacement, DIME, Needs-Based, Human Life Value)
- Premium estimation based on UK actuarial data
- Personalized recommendations
- Responsive design for all devices
- Lloyds Banking Group branding

✅ **Enhanced Security**
- Spring Security with comprehensive headers
- Input validation and sanitization
- No data leakage or persistence
- CSRF protection and secure defaults

✅ **Modern Architecture**
- TypeScript for type safety
- Component-based React architecture
- RESTful API design
- Comprehensive error handling

## Troubleshooting
- **Port 8080 in use**: Change server.port in application.yml
- **Build fails**: Ensure Java 17+ and Maven 3.6+ are installed
- **Frontend issues**: Check Node.js version (18+ required)
- **API errors**: Check /api/insurance/health endpoint

## Support
For technical issues, check the application logs and API documentation at /swagger-ui.html 