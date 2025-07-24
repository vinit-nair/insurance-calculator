# Multi-stage Dockerfile for UK Life Insurance Calculator
# Stage 1: Build React Frontend
FROM node:18-alpine AS frontend-builder

# Set working directory for frontend
WORKDIR /app/frontend

# Copy frontend package files
COPY src/main/frontend/package*.json ./

# Install frontend dependencies
RUN npm ci --only=production

# Copy frontend source code
COPY src/main/frontend/ ./

# Build frontend for production
RUN npm run build

# Stage 2: Build Spring Boot Application
FROM maven:3.9.4-eclipse-temurin-17 AS backend-builder

# Set working directory for backend
WORKDIR /app

# Copy Maven configuration
COPY pom.xml ./

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src/ ./src/

# Build the application
RUN mvn clean package -DskipTests

# Stage 3: Production Runtime
FROM eclipse-temurin:17-jre-alpine

# Install necessary packages
RUN apk add --no-cache \
    curl \
    tzdata \
    && rm -rf /var/cache/apk/*

# Set timezone to UK
ENV TZ=Europe/London

# Create application user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy the built JAR from the backend builder stage
COPY --from=backend-builder /app/target/insurance-calculator-1.0.0.jar app.jar

# Copy the built frontend from the frontend builder stage
COPY --from=frontend-builder /app/frontend/build/ ./static/

# Create logs directory
RUN mkdir -p logs && chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/lloyds-insurance-calculator/actuator/health || exit 1

# JVM options for production
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 