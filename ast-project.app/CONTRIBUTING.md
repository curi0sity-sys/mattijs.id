# Contributing to Pascal Compiler API

Thank you for your interest in contributing to the Pascal Compiler API! This document provides guidelines for contributing to the project.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/your-username/pascal-compiler-api.git`
3. Create a new branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test your changes locally
6. Commit your changes: `git commit -am 'Add some feature'`
7. Push to the branch: `git push origin feature/your-feature-name`
8. Submit a pull request

## Development Setup

### Prerequisites

- Java 17+ (GraalVM recommended)
- Maven 3.9+
- Docker and Docker Compose
- Git

### Local Development

1. Build the project:
```bash
mvn clean install
```

2. Run tests:
```bash
mvn test
```

3. Run the application:
```bash
mvn spring-boot:run
```

## Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods small and focused
- Write unit tests for new features

## Adding New Pascal Examples

To add a new Pascal example file:

1. Add the `.pas` file to `src/main/resources/pascal-examples/`
2. Update the whitelist in `PascalCompilerService.java`:
```java
WHITELISTED_FILES.put("newfile.pas", new PascalFileDto(
    "newfile.pas", "Display Name", "Description"));
```
3. Test compilation with the new file
4. Update README.md with the new file information

## Testing

### Running All Tests

```bash
mvn test
```

### Testing the API

Use the provided test scripts:

**Linux/macOS:**
```bash
./test-api.sh
```

**Windows:**
```powershell
.\test-api.ps1
```

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

## Pull Request Process

1. Ensure all tests pass
2. Update the README.md with details of changes if applicable
3. Update the CHANGELOG.md if present
4. The PR will be merged once you have approval from a maintainer

## Reporting Bugs

When reporting bugs, please include:

- Your operating system
- Java version
- Steps to reproduce the bug
- Expected behavior
- Actual behavior
- Any error messages or logs

## Suggesting Features

Feature suggestions are welcome! Please open an issue with:

- Clear description of the feature
- Use case for the feature
- Any implementation ideas you have

## Code of Conduct

- Be respectful and inclusive
- Welcome newcomers
- Focus on constructive feedback
- Respect differing viewpoints

## Questions?

If you have questions, feel free to:

- Open an issue
- Start a discussion
- Contact the maintainers

Thank you for contributing!

