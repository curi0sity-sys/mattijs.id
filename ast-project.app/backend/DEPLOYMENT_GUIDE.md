# Pascal Compiler API - Deployment Guide

This guide walks you through deploying the Pascal Compiler API to production with Docker and Traefik.

## Prerequisites

- A server with Docker and Docker Compose installed (Linux recommended)
- A domain name pointing to your server
- Docker Hub account
- Basic knowledge of Docker and Linux commands

## Step 1: Prepare Your Environment

### 1.1 Configure Environment Variables

```bash
cd pascal-compiler-api
cp env.example .env
nano .env
```

Update the following variables:
```env
DOCKER_USERNAME=your-dockerhub-username
API_PORT=8080
API_HOSTNAME=api.yourdomain.com
CORS_ALLOWED_ORIGINS=https://yourdomain.com,https://www.yourdomain.com
ACME_EMAIL=your-email@example.com
```

### 1.2 DNS Configuration

Ensure your domain's DNS records point to your server:
```
A    api.yourdomain.com    → Your-Server-IP
```

## Step 2: Build the Application

### Option A: Build Locally and Push to Docker Hub

**On Windows (PowerShell):**
```powershell
.\build.ps1
.\deploy.ps1
```

**On Linux/macOS:**
```bash
chmod +x build.sh deploy.sh
./build.sh
./deploy.sh
```

### Option B: Use GitHub Actions (Recommended)

1. Fork the repository to your GitHub account

2. Add GitHub Secrets:
   - Go to Settings → Secrets and variables → Actions
   - Add `DOCKER_USERNAME` (your Docker Hub username)
   - Add `DOCKER_PASSWORD` (your Docker Hub password or access token)

3. Push to main branch:
```bash
git push origin main
```

GitHub Actions will automatically build and push the Docker image.

## Step 3: Deploy to Production Server

### 3.1 Connect to Your Server

```bash
ssh user@your-server-ip
```

### 3.2 Clone the Repository

```bash
git clone https://github.com/your-username/pascal-compiler-api.git
cd pascal-compiler-api
```

### 3.3 Configure Environment

```bash
cp env.example .env
nano .env
```

Update with your production values.

### 3.4 Create Required Directories

```bash
mkdir -p letsencrypt
chmod 600 letsencrypt
```

### 3.5 Start the Services

```bash
docker-compose up -d
```

### 3.6 Verify Deployment

Check if containers are running:
```bash
docker-compose ps
```

Check logs:
```bash
docker-compose logs -f pascal-compiler-api
docker-compose logs -f traefik
```

Access the API:
```bash
curl https://api.yourdomain.com/api/health
```

## Step 4: Testing the API

### 4.1 List Available Files

```bash
curl https://api.yourdomain.com/api/list
```

### 4.2 View a Pascal File

```bash
curl https://api.yourdomain.com/api/view/HelloOnce.pas
```

### 4.3 Compile a Pascal File

```bash
curl -X POST https://api.yourdomain.com/api/compile/factorial.pas
```

## Step 5: Monitoring and Maintenance

### Check Container Health

```bash
docker-compose ps
docker stats
```

### View Logs

```bash
# API logs
docker-compose logs -f pascal-compiler-api

# Traefik logs
docker-compose logs -f traefik

# Last 100 lines
docker-compose logs --tail=100 pascal-compiler-api
```

### Restart Services

```bash
docker-compose restart pascal-compiler-api
```

### Update to New Version

```bash
# Pull latest changes
git pull origin main

# Pull latest Docker image
docker-compose pull

# Restart with new image
docker-compose up -d
```

### Stop Services

```bash
docker-compose down
```

### Remove Everything (including volumes)

```bash
docker-compose down -v
```

## Step 6: SSL/TLS Configuration

Traefik automatically handles SSL/TLS with Let's Encrypt. The certificates are stored in `./letsencrypt/acme.json`.

### Verify SSL Certificate

```bash
curl -vI https://api.yourdomain.com/api/health
```

Look for the certificate information in the output.

### Certificate Renewal

Traefik automatically renews certificates before they expire. No manual action needed.

## Troubleshooting

### Issue: Containers won't start

**Check:**
```bash
docker-compose logs
docker ps -a
```

**Solution:** Check if ports 80, 443, 8080 are already in use:
```bash
sudo netstat -tulpn | grep -E ':(80|443|8080) '
```

### Issue: Cannot access API

**Check:**
1. Firewall rules allow ports 80 and 443:
```bash
sudo ufw status
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
```

2. DNS is correctly configured:
```bash
nslookup api.yourdomain.com
```

3. Traefik dashboard (http://your-server-ip:8081) shows the service

### Issue: SSL certificate errors

**Check:**
1. Email in `.env` is correct
2. Domain is accessible from the internet
3. Let's Encrypt rate limits haven't been exceeded

**Solution:**
```bash
# Remove old certificates
rm -rf letsencrypt/acme.json
# Restart traefik
docker-compose restart traefik
```

### Issue: Compilation errors in API

**Check logs:**
```bash
docker-compose logs pascal-compiler-api | grep ERROR
```

**Increase log level:**
Update `application.properties`:
```properties
logging.level.com.pascal.compiler=TRACE
```

## Security Best Practices

### 1. Use Specific CORS Origins

Don't use `*` in production:
```env
CORS_ALLOWED_ORIGINS=https://yourdomain.com,https://www.yourdomain.com
```

### 2. Enable Rate Limiting (Traefik)

Add to `docker-compose.yml` under traefik command:
```yaml
- "--entrypoints.web.http.ratelimit.average=100"
- "--entrypoints.web.http.ratelimit.burst=50"
```

### 3. Regular Updates

```bash
# Update base images
docker-compose pull
docker-compose up -d

# Update application
git pull
docker-compose up -d --build
```

### 4. Backup Certificates

```bash
# Backup Let's Encrypt certificates
tar -czf letsencrypt-backup-$(date +%Y%m%d).tar.gz letsencrypt/
```

### 5. Monitor Resource Usage

```bash
docker stats
```

## Performance Tuning

### Increase JVM Memory

Update `docker-compose.yml`:
```yaml
environment:
  - JAVA_OPTS=-Xmx2g -Xms1g -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0
```

### Scale Containers

```bash
docker-compose up -d --scale pascal-compiler-api=3
```

Update Traefik labels for load balancing in `docker-compose.yml`.

## Backup and Recovery

### Backup

```bash
# Backup configuration
tar -czf pascal-api-backup-$(date +%Y%m%d).tar.gz \
    .env docker-compose.yml letsencrypt/

# Save to remote location
scp pascal-api-backup-*.tar.gz user@backup-server:/backups/
```

### Recovery

```bash
# Restore configuration
tar -xzf pascal-api-backup-YYYYMMDD.tar.gz

# Start services
docker-compose up -d
```

## Advanced Configuration

### Custom Java Runtime Options

Create a custom Dockerfile or override in docker-compose.yml:
```yaml
environment:
  - JAVA_OPTS=-XX:+UseG1GC -XX:MaxGCPauseMillis=200
```

### Custom Application Properties

Mount a custom `application.properties`:
```yaml
volumes:
  - ./application-prod.properties:/app/config/application.properties:ro
```

### Health Check Customization

Update in `docker-compose.yml`:
```yaml
healthcheck:
  test: ["CMD", "curl", "-f", "http://localhost:8080/api/health"]
  interval: 30s
  timeout: 10s
  retries: 3
  start_period: 60s
```

## Support and Maintenance

### View Traefik Dashboard

Access at: `http://your-server-ip:8081`

### Monitor API Metrics

Enable Spring Boot Actuator endpoints in production by updating `application.properties`:
```properties
management.endpoints.web.exposure.include=health,info,metrics
```

Access at: `https://api.yourdomain.com/actuator/metrics`

## Production Checklist

- [ ] Domain DNS configured
- [ ] `.env` file configured with production values
- [ ] Firewall configured (ports 80, 443 open)
- [ ] Docker and Docker Compose installed
- [ ] CORS origins set to specific domains (not *)
- [ ] SSL certificate obtained successfully
- [ ] API responds to health check
- [ ] All three endpoints tested (list, view, compile)
- [ ] Logs verified for errors
- [ ] Backup strategy in place
- [ ] Monitoring configured
- [ ] Rate limiting configured (optional but recommended)

## Next Steps

1. Set up monitoring with Prometheus/Grafana
2. Implement API authentication if needed
3. Add more Pascal example files
4. Set up automated backups
5. Configure alerts for downtime

For more information, see the main [README.md](README.md).

