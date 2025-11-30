# Pascal Compiler REST API

A REST API wrapper for the Pascal compiler from "Writing Compilers and Interpreters" (3rd Edition) by Ronald Mak. This API provides endpoints to view Pascal source code, compile Pascal programs to JVM bytecode (Jasmin), and list available example files.

## Features

- 🔍 **View** Pascal source code files
- ⚙️ **Compile** Pascal programs to JVM bytecode (Jasmin format)
- 📋 **List** all available whitelisted Pascal example files
- 🐳 **Docker** ready with GraalVM Java 23
- 🔒 **Traefik** integration for SSL/TLS support
- 🌐 **CORS** enabled for frontend integration
- 📊 **Health checks** and monitoring

## Technology Stack

- **Java 17** (LTS) - compatible with GraalVM
- **Spring Boot 3.3.5** for REST API
- **Maven** for dependency management
- **Docker** for containerization
- **Traefik** for reverse proxy and SSL

## Quick Start

### Prerequisites

- Docker and Docker Compose
- Java 17+ (GraalVM recommended) for local development
- Maven 3.9+ for local development

### Running with Docker Compose

1. Clone the repository:
```bash
git clone <repository-url>
cd pascal-compiler-api
```

2. Copy and configure environment variables:
```bash
cp env.example .env
# Edit .env with your settings
```

3. Build and run with Docker Compose:
```bash
docker-compose up -d
```

4. Access the API at `http://localhost:8080`

### Running Locally

1. Build the project:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/pascal-compiler-api-1.0.0.jar
```

## API Endpoints

### 1. List Available Files

**GET** `/api/list`

Lists all whitelisted Pascal files available for viewing and compilation.

**Response:**
```json
{
  "files": [
    {
      "filename": "HelloOnce.pas",
      "displayName": "Hello Once",
      "description": "Simple hello world program"
    },
    {
      "filename": "Xref.pas",
      "displayName": "Cross Reference",
      "description": "Cross-reference generator from the book"
    }
  ],
  "totalCount": 7
}
```

### 2. View Source Code

**GET** `/api/view/{filename}`

Returns the source code of the specified Pascal file.

**Parameters:**
- `filename` (path) - Name of the Pascal file (e.g., "HelloOnce.pas")

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

**POST** `/api/compile/{filename}`

Compiles the specified Pascal file to JVM bytecode (Jasmin format).

**Parameters:**
- `filename` (path) - Name of the Pascal file (e.g., "factorial.pas")

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
  "jasminCode": ".class public factorial\n.super java/lang/Object\n...",
  "errors": "",
  "compilationTimeMs": 1523,
  "stats": {
    "sourceLines": 42,
    "syntaxErrors": 0,
    "elapsedTime": 1.52
  }
}
```

### Health Check

**GET** `/api/health`

Returns the health status of the API.

## Available Pascal Files

The following Pascal example files are whitelisted and available:

1. **HelloOnce.pas** - Simple hello world program
2. **HelloMany.pas** - Hello world with loop
3. **factorial.pas** - Factorial calculation
4. **newton1.pas** - Square root using Newton's method
5. **ForTest.pas** - Testing FOR loops
6. **IfTest.pas** - Testing IF statements
7. **Xref.pas** - Cross-reference generator (from the book)

## Docker Deployment

### Building the Docker Image

```bash
docker build -t your-dockerhub-username/pascal-compiler-api:latest .
```

### Pushing to Docker Hub

```bash
docker login
docker push your-dockerhub-username/pascal-compiler-api:latest
```

### Running with Docker

```bash
docker run -d \
  -p 8080:8080 \
  -e CORS_ALLOWED_ORIGINS="http://localhost:3000" \
  --name pascal-compiler-api \
  your-dockerhub-username/pascal-compiler-api:latest
```

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SERVER_PORT` | 8080 | Port for the API server |
| `SERVER_ADDRESS` | 0.0.0.0 | Server bind address |
| `CORS_ALLOWED_ORIGINS` | * | Comma-separated list of allowed origins |
| `API_HOSTNAME` | api.localhost | Hostname for Traefik routing |
| `DOCKER_USERNAME` | - | Your Docker Hub username |
| `ACME_EMAIL` | - | Email for Let's Encrypt certificates |

## Architecture

```
┌─────────────┐      ┌──────────┐      ┌─────────────────┐
│   Frontend  │ ───> │ Traefik  │ ───> │  Pascal API     │
│  (Browser)  │      │  (SSL)   │      │ (Spring Boot)   │
└─────────────┘      └──────────┘      └─────────────────┘
                                                │
                                                ▼
                                        ┌─────────────────┐
                                        │   WCI Compiler  │
                                        │  (Chapter 18)   │
                                        └─────────────────┘
```

## Development

### Project Structure

```
pascal-compiler-api/
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/pascal/compiler/
│       │   │   ├── api/              # REST controllers
│       │   │   ├── service/          # Business logic
│       │   │   └── config/           # Configuration
│       │   └── wci/                  # WCI compiler code
│       └── resources/
│           ├── application.properties
│           └── pascal-examples/      # Pascal source files
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

### Adding New Pascal Files

1. Add the `.pas` file to `src/main/resources/pascal-examples/`
2. Update the whitelist in `PascalCompilerService.java`:
```java
WHITELISTED_FILES.put("newfile.pas", new PascalFileDto(
    "newfile.pas", "Display Name", "Description"));
```

### Running Tests

```bash
mvn test
```

## Traefik Integration

The `docker-compose.yml` includes Traefik for reverse proxy and SSL termination:

- Traefik Dashboard: `http://localhost:8081`
- Automatic SSL with Let's Encrypt
- HTTP to HTTPS redirect
- Docker service discovery

## Troubleshooting

### Issue: Compilation Fails

**Solution:** Check the logs for syntax errors in the Pascal source code:
```bash
docker logs pascal-compiler-api
```

### Issue: CORS Errors

**Solution:** Update the `CORS_ALLOWED_ORIGINS` environment variable to include your frontend URL.

### Issue: Container Won't Start

**Solution:** Check if port 8080 is already in use:
```bash
docker ps
netstat -an | grep 8080
```

## License

This project is based on the source code from "Writing Compilers and Interpreters" (3rd Edition) by Ronald Mak.

## References

- Book Website: https://apropos-logic.com/books/wci/
- Original Source: https://apropos-logic.com/books/wci/ (Chapter 18)
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- GraalVM: https://www.graalvm.org/

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues and questions, please open an issue on the GitHub repository.

