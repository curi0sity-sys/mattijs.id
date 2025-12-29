# Pascal Compiler API - Quick Start Guide

## 🚀 Your Project is Ready!

The Pascal Compiler REST API is built, tested, and ready to deploy!

---

## ✅ What's Working Right Now

All three API endpoints are **verified working**:

1. **GET /api/list** - Lists all 7 Pascal example files ✅
2. **GET /api/view/{filename}** - Views Pascal source code ✅
3. **POST /api/compile/{filename}** - Compiles to JVM bytecode ✅

Current Status: **Running on http://localhost:8080**

---

## 📦 Project Location

```
C:\Users\pranj\Documents\pv\WCI-source-files\pascal-compiler-api\
```

---

## 🎯 Next Steps to Deploy to GitHub & Docker Hub

### Step 1: Create GitHub Repository

1. Go to https://github.com/new
2. Create a new repository (e.g., `pascal-compiler-api`)
3. **Don't** initialize with README (we already have one)

### Step 2: Push to GitHub

```powershell
cd C:\Users\pranj\Documents\pv\WCI-source-files\pascal-compiler-api

# Add your GitHub repository
git remote add origin https://github.com/YOUR-USERNAME/pascal-compiler-api.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### Step 3: Set Up GitHub Actions (Optional but Recommended)

1. Go to your GitHub repository → Settings → Secrets and variables → Actions
2. Add these secrets:
   - `DOCKER_USERNAME`: Your Docker Hub username
   - `DOCKER_PASSWORD`: Your Docker Hub token/password

Now every push to `main` will automatically build and deploy to Docker Hub!

### Step 4: Build Docker Image Locally

**Update env.example with your Docker Hub username:**
```powershell
cd C:\Users\pranj\Documents\pv\WCI-source-files\pascal-compiler-api
Copy-Item env.example .env
notepad .env  # Edit DOCKER_USERNAME
```

**Build:**
```powershell
.\build.ps1
```

This will:
- Build the Spring Boot JAR
- Create a Docker image with GraalVM

### Step 5: Deploy to Docker Hub

```powershell
.\deploy.ps1
```

This will:
- Log you into Docker Hub
- Tag the image with timestamp
- Push both `latest` and timestamped versions

---

## 🧪 Testing the API

### Test All Endpoints (Automated)

```powershell
.\test-api.ps1
```

### Manual Testing

**1. List all files:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/list" | ConvertTo-Json
```

**2. View a file:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/view/HelloOnce.pas" | ConvertTo-Json
```

**3. Compile a file:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/compile/factorial.pas" -Method Post | ConvertTo-Json
```

---

## 🐳 Running with Docker

### Option 1: Run Just the API

```powershell
docker run -d -p 8080:8080 YOUR-DOCKERHUB-USERNAME/pascal-compiler-api:latest
```

### Option 2: Run Full Stack (with Traefik)

**Edit docker-compose.yml** with your settings, then:

```powershell
docker-compose up -d
```

This starts:
- Pascal Compiler API
- Traefik reverse proxy with SSL support

Access API at: `https://api.yourdomain.com`

---

## 📝 Quick Reference

### Project Structure
```
pascal-compiler-api/
├── src/main/java/
│   ├── com/pascal/compiler/    # Spring Boot application
│   └── wci/                    # WCI compiler (Chapter 18)
├── src/main/resources/
│   └── pascal-examples/        # 7 whitelisted .pas files
├── Dockerfile                  # Docker configuration
├── docker-compose.yml          # Orchestration with Traefik
├── pom.xml                     # Maven dependencies
└── README.md                   # Full documentation
```

### Whitelisted Pascal Files
1. `HelloOnce.pas` - Simple hello world
2. `HelloMany.pas` - Hello world with loop
3. `factorial.pas` - Factorial calculation
4. `newton1.pas` - Newton's method for square root
5. `ForTest.pas` - FOR loop examples
6. `IfTest.pas` - IF statement examples
7. `Xref.pas` - Cross-reference generator ⭐

### Technology Stack
- Java 17 (LTS)
- Spring Boot 3.3.5
- Maven build system
- Docker with GraalVM
- Traefik for SSL

---

## 🔧 Common Commands

### Development

```powershell
# Build locally
mvn clean package

# Run locally
java -jar target/pascal-compiler-api-1.0.0.jar

# Run with Spring Boot Maven plugin
mvn spring-boot:run
```

### Docker

```powershell
# Build Docker image
docker build -t your-username/pascal-compiler-api:latest .

# Run container
docker run -p 8080:8080 your-username/pascal-compiler-api:latest

# View logs
docker logs -f pascal-compiler-api

# Stop container
docker stop pascal-compiler-api
```

### Git

```powershell
# Check status
git status

# View commit history
git log --oneline

# Create new branch
git checkout -b feature/new-feature

# Push changes
git add .
git commit -m "Your message"
git push
```

---

## 📚 Documentation Files

- **README.md** - Main project documentation
- **DEPLOYMENT_GUIDE.md** - Detailed production deployment guide
- **PROJECT_SUMMARY.md** - Complete project overview
- **TEST_RESULTS.md** - Test results and verification
- **CONTRIBUTING.md** - Guidelines for contributors
- **QUICKSTART.md** - This file!

---

## 🎯 Production Deployment Checklist

- [ ] Repository pushed to GitHub
- [ ] Docker image built locally
- [ ] Docker image pushed to Docker Hub
- [ ] `.env` file configured for production
- [ ] Domain DNS configured
- [ ] SSL certificate email set
- [ ] CORS origins configured (not *)
- [ ] Server firewall configured (ports 80, 443)
- [ ] `docker-compose up -d` on production server
- [ ] Health check verified
- [ ] All endpoints tested in production
- [ ] Monitoring set up

---

## 💡 Tips

### For Local Development
- The API automatically reloads on code changes (Spring DevTools)
- Check logs in console for debugging
- Use Postman or curl for testing

### For Production
- Use specific CORS origins, not `*`
- Enable rate limiting in Traefik
- Set up monitoring and alerts
- Regular backups of configuration
- Keep Docker images updated

### For Customization
- Add more Pascal files in `src/main/resources/pascal-examples/`
- Update whitelist in `PascalCompilerService.java`
- Modify response DTOs for additional data
- Extend endpoints for more functionality

---

## 📞 Support

- **GitHub Issues**: For bugs and features
- **Documentation**: See README.md and other guides
- **Book Reference**: "Writing Compilers and Interpreters" by Ronald Mak

---

## 🎉 Success!

Your Pascal Compiler API is:
- ✅ Built
- ✅ Tested
- ✅ Running locally
- ✅ Ready for GitHub
- ✅ Ready for Docker Hub
- ✅ Production-ready

**You're all set!** 🚀

Just follow the "Next Steps to Deploy" above to get it on GitHub and Docker Hub!

