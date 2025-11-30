#!/bin/bash
# Test script for Pascal Compiler API

API_URL=${API_URL:-"http://localhost:8080"}

echo "======================================"
echo "  Pascal Compiler API - Test Script"
echo "======================================"
echo ""

echo "Testing health endpoint..."
curl -s ${API_URL}/api/health
echo ""
echo ""

echo "Testing list endpoint..."
curl -s ${API_URL}/api/list | jq .
echo ""

echo "Testing view endpoint (HelloOnce.pas)..."
curl -s ${API_URL}/api/view/HelloOnce.pas | jq .
echo ""

echo "Testing compile endpoint (factorial.pas)..."
curl -s -X POST ${API_URL}/api/compile/factorial.pas | jq .
echo ""

echo "======================================"
echo "  All tests completed!"
echo "======================================"

