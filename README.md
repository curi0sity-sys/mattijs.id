# mattijs.id, ast-project.app, {monitoring, guacamole, backups, storage app(storage.mattijs.id), behind 2fa - a nextcloud instance will probably do}, zerosec.nl

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
  <li>The compiler project: It needs to support k8s and sticky sessions, I want the needed amount of hetzner servers to handle the load of a kubernetes setup and the 2 applications. On the bright side: 1 version of the application can already host a lot but we need traefik to have a couple of frontends and more backends to handle the traffic and have sticky sessions to handle the proper file io for each frontend. All we need is about a 1:10 comparability since the Java side is heavier than the frontend which is just html and javascript in the end. And I need the Java compiler backend to work in GraalVM with https://quarkus.io/  That's probably christmas. </li>
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
        <li><li>Storage app (storage.zerosec.nl), needs a better domain name.. | probably a nextcloud instance?
      <ul>
        <li><It's a combination of dropbox, tresorit, megaupload and custom filemounts with sftp/ssh with hetzner storage boxes
          <ul>
            <li>Files are immediately uploaded, locally it's a proxy just as with tresorit's solution. Make available offline switch is everywhere, on every device, switch all on/off is available.</li>
            <li>Per user a unique salt and username. From complaint 1 you're permabanned except my own username. I ain't moderating this crap. Through complaint form you're files are removed when any form of abuse is noticed and all your files are removed. So usage of this service will need a whole bunch of trust in not getting reported for no reason.</li>
            <li>Storage app domain reference on blog, .io for the proxy name.</li>
            <li>Desktop apps for macos for custom filenames same as uploader. Written in Java Vaadin so windows immediately has support as well as linux desktop apps. Simple android app with read-only functionalities and auto-upload of photo's to specific folder.</li>
            <li>Files are aes encrypted stored on s3 to start but we support all other sftp/ssh mounts and most other storage facilities like google cloud. Migrated with a single click because we store all file references from the start.</li>
            <li>Dropbox: borrow the interface for file sharing and sync algorithm speeds. Sync algo probably written in c++/c or Go.</li>
            <li>Duplicati fork in this product for all backup related functions. We just need the entire piece of software and it's pretty much good to go as it is. It just needs to de build into this piece of software.</li>
            <li>Vaadin web and desktop applications need to support duplicati as well.</li>
            <li>Application is run through a docker container on each platform we supported so no manual installation is required.</li>
            <li>I get the admin account. File history for everyone for 365 days.I get to set file sizes per mime-type for all files uploaded</li>
            <li>Megaupload and megatransfer file ideas are solved as well with this product as it needs to have those functions as well. The main difference is having a simple acount as we won't do anonymous uploads.</li>
            <li>Accounts in redis cache, file references in redis cache as well. Encrypted MySQL database storage for the rest.</li>
            <li>A tab with all remote mounts for the ssh / sftp mounts</li>
          </ul>
        </li>
      </ul>
  </li></li>
      </li>
    </ul>
  </li>
  <li>Hetzner Storage box for photo's. (I'll do this)</li>
  
  <li>Home media server: I need a plexamp setup with redis or dragonfly cache behind it. So music is loaded instantly into memory. Or the fasted in memory cache available with its settings to about 120GB.</li>
  <li>Zerosec.nl (own company in a bit)</li>
  <li>CachyOS script to install it flawlessly on a Macbook pro</li>
</ul>

#optional 
https://github.com/fosrl/pangolin bekijken voor wireguard 2fa shizzle
 
