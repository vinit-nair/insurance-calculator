# 🏦 UK Life Insurance Calculator

A comprehensive, UK-compliant life insurance calculator built for Lloyds Banking Group. This application provides accurate insurance coverage recommendations using multiple actuarial methods and real-time UK market data.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![React](https://img.shields.io/badge/React-18.2.0-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![TypeScript](https://img.shields.io/badge/TypeScript-4.9.5-blue)

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### 🎯 Core Functionality
- **Multi-Method Coverage Calculation**: Income Replacement, DIME, Needs Analysis, Human Life Value
- **UK Market Integration**: Real-time ABI rates and UK actuarial standards
- **Dynamic Premium Estimation**: Age, gender, smoking, term length, and inflation protection factors
- **Step-by-Step Form Interface**: Progressive disclosure with validation
- **Real-Time Progress Tracking**: Visual calculation progress with detailed steps
- **Responsive Design**: Mobile-first approach with modern UI/UX

### 🏛️ UK Compliance
- **FCA Regulatory Compliance**: Follows Financial Conduct Authority guidelines
- **ABI Market Data**: Association of British Insurers integration
- **UK Mortality Tables**: Continuous Mortality Investigation (CMI) data
- **UK Tax Considerations**: Proper handling of UK insurance tax implications
- **Data Protection**: GDPR-compliant data handling

### 🎮 User Experience
- **Gamification System**: Achievement badges and progress tracking
- **Interactive Glossary**: Collapsible insurance terminology guide
- **Visual Progress Indicators**: Real-time calculation progress
- **Accessibility**: WCAG 2.1 AA compliant interface
- **Error Handling**: Comprehensive validation and user feedback

## 🛠️ Technology Stack

### Backend
- **Java**: 17 (LTS)
- **Spring Boot**: 3.2.0
- **Spring Security**: Latest
- **Spring Validation**: Input validation and sanitization
- **SpringDoc OpenAPI**: 2.2.0 (Swagger documentation)
- **Maven**: Build and dependency management

### Frontend
- **React**: 18.2.0
- **TypeScript**: 4.9.5
- **Axios**: 1.6.2 (HTTP client)
- **React Scripts**: 5.0.1
- **CSS3**: Modern styling with animations

### Development Tools
- **Node.js**: 18.17.0+
- **npm**: 9.6.7+
- **Maven**: 3.8.0+
- **Git**: Version control

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

### Required Software
- **Java Development Kit (JDK)**: 17 or higher
- **Node.js**: 18.17.0 or higher
- **npm**: 9.6.7 or higher
- **Maven**: 3.8.0 or higher
- **Git**: Latest version

### System Requirements
- **Operating System**: Windows 10+, macOS 10.15+, or Linux
- **Memory**: Minimum 4GB RAM (8GB recommended)
- **Storage**: 2GB free disk space
- **Network**: Internet connection for npm packages and Maven dependencies

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/your-org/uk-insurance-calculator.git
cd uk-insurance-calculator
```

### 2. Backend Setup
```bash
# Navigate to project root
cd uk-insurance-calculator

# Clean and compile the project
mvn clean compile

# Run tests to ensure everything works
mvn test
```

### 3. Frontend Setup
```bash
# Navigate to frontend directory
cd src/main/frontend

# Install dependencies
npm install

# Verify installation
npm run build
```

### 4. Environment Configuration
The application uses default configurations for development. For production, create environment-specific configuration files.

## 🏃‍♂️ Running the Application

### Option 1: Using the Start Script (Recommended)
```bash
# From project root
./start-app.bat  # Windows
# OR
chmod +x start-app.sh && ./start-app.sh  # Linux/macOS
```

### Option 2: Manual Startup

#### Start Backend (Terminal 1)
```bash
# From project root
mvn spring-boot:run
```
Backend will be available at: `http://localhost:8080/lloyds-insurance-calculator`

#### Start Frontend (Terminal 2)
```bash
# From frontend directory
cd src/main/frontend
npm start
```
Frontend will be available at: `http://localhost:3000`

### Option 3: Development Mode
```bash
# Backend with hot reload
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"

# Frontend with hot reload
npm start
```

## 📚 API Documentation

### Swagger UI
Once the application is running, access the interactive API documentation at:
```
http://localhost:8080/lloyds-insurance-calculator/swagger-ui.html
```

### Key Endpoints

#### Calculate Insurance
```http
POST /api/insurance/calculate
Content-Type: application/json

{
  "age": 35,
  "gender": "female",
  "smoking": "no",
  "annualIncome": 50000,
  "monthlyExpenses": 2500,
  "existingCoverage": 100000,
  "savings": 50000,
  "mortgage": 200000,
  "otherDebts": 15000,
  "funeralCosts": 5000,
  "dependents": 2,
  "spouseIncome": 30000,
  "educationFund": 25000,
  "coveragePeriod": "20",
  "inflationProtection": "no"
}
```

#### Get Progress
```http
GET /api/insurance/progress/{sessionId}
```

#### Health Check
```http
GET /actuator/health
```

## 📁 Project Structure

```
uk-insurance-calculator/
├── src/
│   ├── main/
│   │   ├── java/com/lloyds/insurance/
│   │   │   ├── config/           # Spring configuration
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/             # Data transfer objects
│   │   │   ├── service/         # Business logic
│   │   │   └── InsuranceCalculatorApplication.java
│   │   ├── frontend/            # React application
│   │   │   ├── public/          # Static assets
│   │   │   ├── src/
│   │   │   │   ├── components/  # React components
│   │   │   │   ├── services/    # API services
│   │   │   │   ├── types/       # TypeScript types
│   │   │   │   └── constants/   # Application constants
│   │   │   └── package.json
│   │   └── resources/
│   │       └── application.yml  # Application configuration
│   └── test/                    # Test files
├── logs/                        # Application logs
├── target/                      # Compiled artifacts
├── pom.xml                      # Maven configuration
├── start-app.bat               # Windows startup script
└── README.md                   # This file
```

## ⚙️ Configuration

### Application Properties
Key configuration options in `application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /lloyds-insurance-calculator

spring:
  application:
    name: uk-insurance-calculator
  security:
    headers:
      content-security-policy: "default-src 'self'"

uk:
  data:
    enabled: false  # Enable real-time UK data
    timeout: 5000   # API timeout
    cache:
      ttl: 3600     # Cache duration
```

### Environment Variables
```bash
# Development
SPRING_PROFILES_ACTIVE=dev
UK_DATA_ENABLED=false

# Production
SPRING_PROFILES_ACTIVE=prod
UK_DATA_ENABLED=true
```

## 🧪 Testing

### Backend Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=InsuranceCalculationServiceTest

# Run with coverage
mvn test jacoco:report
```

### Frontend Tests
```bash
# Navigate to frontend directory
cd src/main/frontend

# Run tests
npm test

# Run tests with coverage
npm test -- --coverage --watchAll=false
```

### Integration Tests
```bash
# Run integration tests
mvn verify
```

## 🚀 Deployment

### Production Build
```bash
# Build backend JAR
mvn clean package -DskipTests

# Build frontend
cd src/main/frontend
npm run build
```

### Docker Deployment
```dockerfile
# Backend Dockerfile
FROM openjdk:17-jre-slim
COPY target/insurance-calculator-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```



## 🤝 Contributing

### Development Workflow
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

### Code Standards
- **Java**: Follow Google Java Style Guide
- **TypeScript**: Use ESLint and Prettier
- **Commits**: Use conventional commit messages
- **Tests**: Maintain 80%+ code coverage

### Pull Request Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Tests added/updated
- [ ] Documentation updated
- [ ] No breaking changes

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

### Getting Help
- **Documentation**: Check this README and inline code comments
- **Issues**: Create an issue on GitHub
- **Discussions**: Use GitHub Discussions for questions

### Common Issues

#### Port Already in Use
```bash
# Check what's using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # macOS/Linux

# Kill the process or change port in application.yml
```

#### Node Modules Issues
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and reinstall
rm -rf node_modules package-lock.json
npm install
```

#### Maven Dependencies
```bash
# Clean and reinstall dependencies
mvn clean install -U
```



## 🔒 Security

- **Input Validation**: Comprehensive sanitization
- **CORS Configuration**: Proper cross-origin settings
- **Content Security Policy**: XSS protection
- **HTTPS**: TLS/SSL encryption
- **Rate Limiting**: API abuse prevention

---

*For internal use only. Not for public distribution.* 