# mattijs.id, ast-project.app, storage - a nextcloud instance fork, zerosec.nl

## Setup
<ul>
  <li>A simple script in a prepared docker, you provide a public ip address and a ssh public key to automatically setup the following on any ssh capable hosting setup.
  That docker setup and it's folders will live in this repo as well</li>
</ul>

## Infrastructure - all of it in a single docker compose for now
<ul>
  <li>Blog (mattijs.id) - # done
    <ul>
      <li>+bunnyhopcdn all data + migration of current (done with magicpages)</li> #done
      <li>Rsync.net backups with seperate account(optional)</li> #to be done
    </ul>
  </li>
  <li>
    Compiler (compiler-project.ast) - on hosting.nl, got the domain ready to go. The VPS will need to be bought. 2 cores, 2GB ram, rest default.
    <ul>
      <li>VueJS 3 frontend, with exactly the same looks and fonts as the docker image, with adjustable domain name, perhaps a simple config file</li>
      <li>Backend: Got most of the source code ready, it just needs to be upgraded to the latest GraalVM, Quarkus.io needs to be used too. And cleanup the source code to only have the API calls.</li>
      <li>To finish things off: I need nginx SWAG, The rewritten VueJS frontend docker and the upgraded backend docker in a docker-compose, and everything needs to be pointing to ast-project.app domain name so I can just launch a simple VPS, copy and paste the docker compose and get things running instantly. I'll just need to login to docker one time per machine</li>
      <li>Font family and sizes need to be in a configuration file or somewhere adjustable, same with the colours used</li>
      <li>Docker-slim used on the container images to optimize it</li>
      <li>A dockerized backup script that'll backup all of this to an rsync account. This docker image will live in this repo too</li>
      <li>The VPS or hosting solution this'll live on has a completely hardened AlmaLinux setup as base OS. So, IDS/IPS, Lynis runs daily, automatically pentested daily</li>
    </ul>
  </li>
  <li>
    VPN Wireguard, Proxy manager username/pw + 2fa # the year 2031 or something
    <ul>
       <li>Storage app (storage.zerosec.nl), needs a better domain name.. | A Nextcloud instance as a base
        <ul>
          <li>It's a combination of dropbox, tresorit, megaupload and custom filemounts with sftp/ssh with hetzner storage boxes as storage solution.
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
   </li>
  <li>Hetzner Storage box for photo's. (I'll do this)</li>
  
  <li>Home media server: I need a plexamp setup with redis or dragonfly cache behind it. So music is loaded instantly into memory. Or the fasted in memory cache available with its settings to about 120GB.</li>
  <li>Zerosec.nl (own company in a bit)</li>
</ul>
 
