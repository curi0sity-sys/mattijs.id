# Pascal Compiler API - Project Summary

## Overview

This project provides a REST API wrapper for the Pascal compiler from "Writing Compilers and Interpreters" (3rd Edition) by Ronald Mak. It's built with modern Java technologies and is production-ready with Docker containerization.

## Key Features

✅ **Three REST Endpoints:**
- `/api/list` - List all whitelisted Pascal files
- `/api/view/{filename}` - View Pascal source code
- `/api/compile/{filename}` - Compile Pascal to JVM bytecode (Jasmin)

✅ **Modern Tech Stack:**
- Java 17 (LTS) with GraalVM support
- Spring Boot 3.3.5
- Maven for build management
- Docker for containerization
- Traefik for reverse proxy and SSL

✅ **Production Ready:**
- Environment variable configuration
- CORS support for frontend integration
- Health checks and monitoring
- Automatic SSL with Let's Encrypt via Traefik
- Comprehensive error handling
- Logging and debugging support

✅ **Developer Friendly:**
- Complete documentation
- Build and deployment scripts (Windows + Linux/macOS)
- GitHub Actions CI/CD pipeline
- Test scripts for API verification

## Project Structure

```
pascal-compiler-api/
├── .github/
│   └── workflows/
│       └── build-and-deploy.yml    # GitHub Actions CI/CD
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/pascal/compiler/
│       │   │   ├── PascalCompilerApiApplication.java  # Main application
│       │   │   ├── api/
│       │   │   │   ├── PascalCompilerController.java  # REST endpoints
│       │   │   │   ├── GlobalExceptionHandler.java    # Error handling
│       │   │   │   └── dto/                           # Data transfer objects
│       │   │   ├── service/
│       │   │   │   ├── PascalCompilerService.java     # Business logic
│       │   │   │   ├── OutputCapture.java             # Output redirection
│       │   │   │   └── *Exception.java                # Custom exceptions
│       │   │   └── config/
│       │   │       └── WebConfig.java                 # CORS & web config
│       │   └── wci/                                   # WCI compiler (Chapter 18)
│       │       ├── backend/                           # Code generation
│       │       ├── frontend/                          # Parsing
│       │       ├── intermediate/                      # IR & symbol tables
│       │       ├── message/                           # Messaging system
│       │       └── util/                              # Utilities
│       └── resources/
│           ├── application.properties                 # Configuration
│           └── pascal-examples/                       # Pascal source files
│               ├── HelloOnce.pas
│               ├── HelloMany.pas
│               ├── factorial.pas
│               ├── newton1.pas
│               ├── ForTest.pas
│               ├── IfTest.pas
│               └── Xref.pas
│
├── Dockerfile                      # Docker image definition
├── docker-compose.yml              # Docker Compose orchestration
├── pom.xml                         # Maven configuration
├── .gitignore                      # Git ignore rules
├── .dockerignore                   # Docker ignore rules
│
├── build.sh / build.ps1            # Build scripts
├── deploy.sh / deploy.ps1          # Deployment scripts
├── test-api.sh / test-api.ps1      # API test scripts
│
├── README.md                       # Main documentation
├── DEPLOYMENT_GUIDE.md             # Deployment instructions
├── CONTRIBUTING.md                 # Contribution guidelines
├── PROJECT_SUMMARY.md              # This file
├── LICENSE                         # MIT License
└── env.example                     # Environment variable template
```

## Whitelisted Pascal Files

The following 7 Pascal files are included and whitelisted:

1. **HelloOnce.pas** - Simple "Hello, World!" program
2. **HelloMany.pas** - Hello world with loop
3. **factorial.pas** - Factorial calculation (recursive)
4. **newton1.pas** - Square root using Newton's method
5. **ForTest.pas** - Testing FOR loops
6. **IfTest.pas** - Testing IF/THEN/ELSE statements
7. **Xref.pas** - Cross-reference generator (from the book)

## API Endpoints

### 1. List Files
```http
GET /api/list
```

**Response:**
```json
{
  "files": [
    {
      "filename": "HelloOnce.pas",
      "displayName": "Hello Once",
      "description": "Simple hello world program"
    }
  ],
  "totalCount": 7
}
```

### 2. View Source Code
```http
GET /api/view/{filename}
```

**Example:**
```bash
curl http://localhost:8080/api/view/HelloOnce.pas
```

**Response:**
```json
{
  "filename": "HelloOnce.pas",
  "sourceCode": "PROGRAM HelloOnce;\nBEGIN\n  writeln('Hello, world.');\nEND.",
  "lineCount": 4
}
```

### 3. Compile Program
```http
POST /api/compile/{filename}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/compile/factorial.pas
```

**Response:**
```json
{
  "filename": "factorial.pas",
  "success": true,
  "compiledOutput": "...",
  "jasminCode": ".class public factorial\n...",
  "errors": "",
  "compilationTimeMs": 1523,
  "stats": {
    "sourceLines": 42,
    "syntaxErrors": 0,
    "elapsedTime": 1.52
  }
}
```

## Quick Start

### Using Docker Compose (Recommended)

```bash
# 1. Clone and configure
git clone <repository-url>
cd pascal-compiler-api
cp env.example .env
# Edit .env with your settings

# 2. Start services
docker-compose up -d

# 3. Test
curl http://localhost:8080/api/health
```

### Local Development

```bash
# 1. Build
mvn clean package

# 2. Run
java -jar target/pascal-compiler-api-1.0.0.jar

# 3. Test
curl http://localhost:8080/api/list
```

## Technology Decisions

### Why GraalVM Java 23?
- Latest Java features
- Excellent performance
- Native image support (future enhancement)
- Enterprise-grade JVM

### Why Spring Boot 3?
- Industry standard
- Excellent REST support
- Auto-configuration
- Production-ready features (actuator, metrics)
- Large ecosystem

### Why Docker?
- Consistent deployment across environments
- Easy scaling
- Isolation
- Reproducible builds

### Why Traefik?
- Automatic SSL with Let's Encrypt
- Docker integration
- Load balancing
- Modern reverse proxy

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SERVER_PORT` | 8080 | API server port |
| `SERVER_ADDRESS` | 0.0.0.0 | Server bind address |
| `CORS_ALLOWED_ORIGINS` | * | Allowed CORS origins |
| `DOCKER_USERNAME` | - | Docker Hub username |
| `API_HOSTNAME` | api.localhost | Traefik routing hostname |
| `ACME_EMAIL` | - | Let's Encrypt email |

## Build and Deployment

### Build Locally

**Windows:**
```powershell
.\build.ps1
```

**Linux/macOS:**
```bash
./build.sh
```

### Deploy to Docker Hub

**Windows:**
```powershell
.\deploy.ps1
```

**Linux/macOS:**
```bash
./deploy.sh
```

### GitHub Actions

Push to main branch triggers automatic build and deployment to Docker Hub.

## Testing

### Manual Testing

```bash
# Health check
curl http://localhost:8080/api/health

# List files
curl http://localhost:8080/api/list

# View file
curl http://localhost:8080/api/view/HelloOnce.pas

# Compile file
curl -X POST http://localhost:8080/api/compile/factorial.pas
```

### Automated Testing

**Windows:**
```powershell
.\test-api.ps1
```

**Linux/macOS:**
```bash
./test-api.sh
```

## Architecture

```
┌─────────────┐      ┌──────────┐      ┌─────────────────┐
│   Client    │      │          │      │                 │
│  (Browser/  │─────▶│ Traefik  │─────▶│  Pascal API     │
│   cURL)     │      │  (SSL)   │      │ (Spring Boot)   │
└─────────────┘      └──────────┘      └─────────────────┘
                                                │
                                                ▼
                                        ┌─────────────────┐
                                        │   WCI Compiler  │
                                        │   (Chapter 18)  │
                                        │                 │
                                        │  ┌───────────┐  │
                                        │  │  Parser   │  │
                                        │  └─────┬─────┘  │
                                        │        │        │
                                        │  ┌─────▼─────┐  │
                                        │  │  ICode    │  │
                                        │  └─────┬─────┘  │
                                        │        │        │
                                        │  ┌─────▼─────┐  │
                                        │  │ Generator │  │
                                        │  └─────┬─────┘  │
                                        │        │        │
                                        │  ┌─────▼─────┐  │
                                        │  │  Jasmin   │  │
                                        │  │  Bytecode │  │
                                        │  └───────────┘  │
                                        └─────────────────┘
```

## Key Implementation Details

### Output Capture
- `OutputCapture.java` redirects `System.out` to capture compiler output
- Ensures thread-safe operation
- Automatically restores original output stream

### Whitelisting
- Only whitelisted files can be accessed
- Prevents arbitrary file access
- Easy to extend with new files

### Error Handling
- Custom exceptions for different error types
- Global exception handler for consistent API responses
- Detailed error messages with timestamps

### CORS Support
- Configurable via environment variables
- Production-ready with specific origin support
- Pre-flight request handling

## Future Enhancements

### Potential Features
1. Authentication/Authorization (JWT)
2. Rate limiting per user
3. Caching of compiled results
4. WebSocket support for real-time compilation
5. Code execution (not just compilation)
6. Multiple language support (not just Pascal)
7. GraalVM native image compilation
8. Metrics and monitoring dashboard
9. File upload for custom Pascal programs
10. Syntax highlighting in responses

### Performance Optimizations
1. Response caching
2. Connection pooling
3. Async compilation
4. Resource limits per compilation
5. Compilation timeout handling

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## License

MIT License - See [LICENSE](LICENSE) for details.

This project incorporates code from "Writing Compilers and Interpreters" (3rd Edition) by Ronald Mak.

## Resources

- **Book Website:** https://apropos-logic.com/books/wci/
- **Spring Boot:** https://spring.io/projects/spring-boot
- **GraalVM:** https://www.graalvm.org/
- **Docker:** https://www.docker.com/
- **Traefik:** https://traefik.io/

## Support

- GitHub Issues: For bug reports and feature requests
- Documentation: README.md, DEPLOYMENT_GUIDE.md, CONTRIBUTING.md
- Book Reference: "Writing Compilers and Interpreters" (3rd Edition)

## Acknowledgments

- Ronald Mak for the original Pascal compiler implementation
- Spring Boot team for the excellent framework
- GraalVM team for the high-performance JVM
- Docker and Traefik communities

---

**Status:** ✅ Production Ready

**Version:** 1.0.0

**Last Updated:** November 2024

