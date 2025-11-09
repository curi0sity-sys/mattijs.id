# mattijs.id

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
</ul>

## Infrastructure
<ul>
  <li>Blog
    <ul>
      <li>+bunnyhopcdn all data + migration of current</li>
      <li>Redis cache for all MySQL database requests for 24 hours with a tekton script REST hook to clean the cache. Database MySQL hosted in a docker but rarely used. </li>
      <li>All posts are immediately put into Redis Cache, with each change you only update the Redis cache and MySQL Database. The only function for the MySQL database is steady storage and a simple backup.</li>
      <li>Rsync.net backups with seperate account</li>
    </ul>
  </li>
  <li>
    Compiler
    <ul>
      <li>Traefik (compiler-project.ast => mattijs.sh forward without changing url bar name)</li>
    </ul>
  </li>
  <li>Next cloud (filestorage - keepass data, photos of RAW of camera 5k DSLR, the storage adapter is a hetzner storage box of bucks/month of 10TB via SFTP)
    <ul>
      <li>Rsync.net backups with seperate account</li>
    </ul>
  </li>
  <li>
    VPN Wireguard + 2fa 
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
</ul>

#optional 
https://github.com/fosrl/pangolin bekijken voor wireguard 2fa shizzle
https://github.com/dgtlmoon/changedetection.io/tree/master 
