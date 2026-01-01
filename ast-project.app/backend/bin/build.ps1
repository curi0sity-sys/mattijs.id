# PowerShell build script for Pascal Compiler API

Write-Host "======================================"
Write-Host "  Pascal Compiler API - Build Script"
Write-Host "======================================"
Write-Host ""

# Load environment variables if .env exists
if (Test-Path .env) {
    Write-Host "Loading environment variables from .env..."
    Get-Content .env | ForEach-Object {
        if ($_ -notmatch '^#' -and $_ -match '=') {
            $name, $value = $_.Split('=', 2)
            Set-Item -Path "env:$name" -Value $value
        }
    }
}

# Set default Docker username if not provided
$DOCKER_USERNAME = if ($env:DOCKER_USERNAME) { $env:DOCKER_USERNAME } else { "your-dockerhub-username" }

Write-Host "Building Spring Boot application..."
mvn clean package -DskipTests

Write-Host ""
Write-Host "Building Docker image..."
docker build -t "${DOCKER_USERNAME}/pascal-compiler-api:latest" .

Write-Host ""
Write-Host "======================================"
Write-Host "  Build completed successfully!"
Write-Host "======================================"
Write-Host ""
Write-Host "To run locally:"
Write-Host "  java -jar target/pascal-compiler-api-1.0.0.jar"
Write-Host ""
Write-Host "To run with Docker:"
Write-Host "  docker run -p 8080:8080 ${DOCKER_USERNAME}/pascal-compiler-api:latest"
Write-Host ""
Write-Host "To push to Docker Hub:"
Write-Host "  .\deploy.ps1"
Write-Host ""

