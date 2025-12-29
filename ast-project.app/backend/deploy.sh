#!/bin/bash
# Deployment script for Pascal Compiler API

set -e

echo "======================================"
echo "  Pascal Compiler API - Deploy Script"
echo "======================================"
echo ""

# Load environment variables if .env exists
if [ -f .env ]; then
    echo "Loading environment variables from .env..."
    export $(cat .env | grep -v '^#' | xargs)
fi

# Set default Docker username if not provided
DOCKER_USERNAME=${DOCKER_USERNAME:-"your-dockerhub-username"}

if [ "$DOCKER_USERNAME" == "your-dockerhub-username" ]; then
    echo "Error: Please set DOCKER_USERNAME in .env file"
    exit 1
fi

echo "Logging in to Docker Hub..."
docker login

echo ""
echo "Tagging image..."
docker tag ${DOCKER_USERNAME}/pascal-compiler-api:latest ${DOCKER_USERNAME}/pascal-compiler-api:$(date +%Y%m%d-%H%M%S)

echo ""
echo "Pushing to Docker Hub..."
docker push ${DOCKER_USERNAME}/pascal-compiler-api:latest
docker push ${DOCKER_USERNAME}/pascal-compiler-api:$(date +%Y%m%d-%H%M%S)

echo ""
echo "======================================"
echo "  Deployment completed successfully!"
echo "======================================"
echo ""
echo "Image pushed to Docker Hub:"
echo "  ${DOCKER_USERNAME}/pascal-compiler-api:latest"
echo ""

