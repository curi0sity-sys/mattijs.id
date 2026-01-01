# Multi-stage build for Pascal Compiler API
# Stage 1: Build with Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime with GraalVM
FROM ghcr.io/graalvm/jdk-community:17

# Install Jasmin assembler (needed for bytecode assembly)
RUN microdnf install -y wget unzip && \
    wget https://github.com/davidar/jasmin/releases/download/v2.5/jasmin-2.5.jar -O /opt/jasmin.jar && \
    microdnf clean all

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Copy Pascal examples
COPY --from=build /app/src/main/resources/pascal-examples /app/pascal-examples

# Environment variables for configuration
ENV SERVER_PORT=8080 \
    SERVER_ADDRESS=0.0.0.0 \
    CORS_ALLOWED_ORIGINS=* \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

