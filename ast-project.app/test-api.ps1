# PowerShell test script for Pascal Compiler API

$API_URL = if ($env:API_URL) { $env:API_URL } else { "http://localhost:8080" }

Write-Host "======================================"
Write-Host "  Pascal Compiler API - Test Script"
Write-Host "======================================"
Write-Host ""

Write-Host "Testing health endpoint..."
Invoke-RestMethod -Uri "$API_URL/api/health"
Write-Host ""
Write-Host ""

Write-Host "Testing list endpoint..."
Invoke-RestMethod -Uri "$API_URL/api/list" | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "Testing view endpoint (HelloOnce.pas)..."
Invoke-RestMethod -Uri "$API_URL/api/view/HelloOnce.pas" | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "Testing compile endpoint (factorial.pas)..."
Invoke-RestMethod -Uri "$API_URL/api/compile/factorial.pas" -Method Post | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "======================================"
Write-Host "  All tests completed!"
Write-Host "======================================"

