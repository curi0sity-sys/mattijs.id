#!/bin/bash
# Build script for Pascal Compiler API

set -e

echo "======================================"
echo "  Pascal Compiler API - Build Script"
echo "======================================"
echo ""

# Load environment variables if .env exists
if [ -f .env ]; then
    echo "Loading environment variables from .env..."
    export $(cat .env | grep -v '^#' | xargs)
fi

# Set default Docker username if not provided
DOCKER_USERNAME=${DOCKER_USERNAME:-"your-dockerhub-username"}

echo "Building Spring Boot application..."
mvn clean package -DskipTests

echo ""
echo "Building Docker image..."
docker build -t ${DOCKER_USERNAME}/pascal-compiler-api:latest .

echo ""
echo "======================================"
echo "  Build completed successfully!"
echo "======================================"
echo ""
echo "To run locally:"
echo "  java -jar target/pascal-compiler-api-1.0.0.jar"
echo ""
echo "To run with Docker:"
echo "  docker run -p 8080:8080 ${DOCKER_USERNAME}/pascal-compiler-api:latest"
echo ""
echo "To push to Docker Hub:"
echo "  ./deploy.sh"
echo ""

