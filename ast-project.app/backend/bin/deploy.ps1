# PowerShell deployment script for Pascal Compiler API

Write-Host "======================================"
Write-Host "  Pascal Compiler API - Deploy Script"
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

if ($DOCKER_USERNAME -eq "your-dockerhub-username") {
    Write-Host "Error: Please set DOCKER_USERNAME in .env file" -ForegroundColor Red
    exit 1
}

Write-Host "Logging in to Docker Hub..."
docker login

Write-Host ""
$timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
Write-Host "Tagging image..."
docker tag "${DOCKER_USERNAME}/pascal-compiler-api:latest" "${DOCKER_USERNAME}/pascal-compiler-api:${timestamp}"

Write-Host ""
Write-Host "Pushing to Docker Hub..."
docker push "${DOCKER_USERNAME}/pascal-compiler-api:latest"
docker push "${DOCKER_USERNAME}/pascal-compiler-api:${timestamp}"

Write-Host ""
Write-Host "======================================"
Write-Host "  Deployment completed successfully!"
Write-Host "======================================"
Write-Host ""
Write-Host "Image pushed to Docker Hub:"
Write-Host "  ${DOCKER_USERNAME}/pascal-compiler-api:latest"
Write-Host ""

