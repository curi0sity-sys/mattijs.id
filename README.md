# mattijs.id, ast-project.app, {monitoring, guacamole, backups}

Hetzner hosted

## Preparations
<ul>
  <li>The current setup? Domains are with a dutch company (hosting.nl) which I'll migrate, midnight hosting has the blog data which needs a final export to the hetzner one</li>
  <li>And we need all current images to re-migrate to bunnyhopcdn.</li>
</ul>

## Setup
<ul>
  <li>Hetzner cloud instance to trigger the tekton with a public ip address en een ssh public key to automatically setup the following on any ssh server. In this case, a hetzner auctioned server I'll provide.<br/>
  Script to do this can be pulled from this github repo so we have it centralized</li>
  <li>Tekton script to have one command to update the entire infrastructure. With automatic rollback to the latest working version if it fails.</li>
  <li>Short term, and my first move: Compiler project in traefik and a docker-compose on 1 hetzner server. Thats probably between now and 5 december. Because I can do this by myself if I have 100 bucks.</li>
  <li>The compiler project: It needs to support k8s and sticky sessions, I want the needed amount of hetzner servers to handle the load of a kubernetes setup and the 2 applications. On the bright side: 1 version of the application can already host a lot but we need traefik to have a couple of frontends and more backends to handle the traffic and have sticky sessions to handle the proper file io for each frontend. All we need is about a 1:10 comparability since the Java side is heavier than the frontend which is just html and javascript in the end. That's probably christmas. </li>
</ul>

## Infrastructure
<ul>
  <li>Blog (mattijs.id)
    <ul>
      <li>+bunnyhopcdn all data + migration of current</li>
      <li>Redis cache for all MySQL database requests for 24 hours with a tekton script REST hook to clean the cache. Database MySQL hosted in a docker but rarely used. </li>
      <li>All posts are immediately put into Redis Cache, with each change you only update the Redis cache and MySQL Database. The only function for the MySQL database is steady storage and a simple backup.</li>
      <li>Rsync.net backups with seperate account</li>
    </ul>
  </li>
  <li>
    Compiler (compiler-project.ast)
    <ul>
      <li>Traefik for ssl certs</li>
      <li>Backup container images to rsync account (once, i can do this as well)</li>
    </ul>
  </li>
  <li>
    VPN Wireguard, Proxy manager username/pw + 2fa
    <ul>
      <li>Hoarder
        <ul>
          Monitoring stack
          <li>Prometheus</li>
          <li>Grafana</li>
          <li>Alert Manager</li>
        </ul>
        <ul>
          <li>Guacamole</li>
          <li>(Amazon Ec2 instances x7 or 20 with all AV/EDR software a pop to do some malware dev or OSEE stuff through Guacamole)</li>
        </ul>
        <ul>
          <li>Rsync.net backups with seperate account [backup van alles achter 2fa]</li>
        </ul>
      </li>
    </ul>
  </li>
  <li>Hetzner Storage box for photo's. (I'll do this)</li>
</ul>

#optional 
https://github.com/fosrl/pangolin bekijken voor wireguard 2fa shizzle
 
