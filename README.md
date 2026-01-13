# mattijs.id, ast-project.app, zerosec.nl

## Setup
<ul>
  <li>AlmaLinux OS - completely hardened to only run a k8s cluster with the below infrastructure. The scripting to do that lives in this repo in a seperate folder.</li>
  <li>First step is running this on a single server I've bought. Which is fine for the next year. Once the hardened OS and the below setup is done, I'll buy 2 more servers and setup a k8s cluster and include sticky sessions for the compiler</li>
</ul>

## Infrastructure
<ul>
  <li>Blog (mattijs.id) - # done
    <ul>
      <li>+bunnyhopcdn all data + migration of current (done with magicpages)</li> #done
      <li>Rsync.net backups with seperate account(optional)</li> #to be done
    </ul>
  </li>
  <li>
    Compiler (compiler-project.ast) - on hosting.nl, got the domain ready to go.
    <ul>
      <li>Frontend</li>
        <ul>
           <li>VueJS 3 frontend: left column list of whitelisted files (20% screensize). To the right of that a Pascal code view of the given file (40%), and the column next to it the JVM code (40%), I need a simple config file for the column sizes, fonts and colours. It needs to be desktop friendly, resizeable from 800x600 to 4k, we can skip a mobile view</li>
          <li>Font family and sizes need to be in a configuration file or somewhere adjustable, same with the colours used. </li>
          <li>Considering the pascal code is changeable, every change needs to be reflected in the compiled JVM code view as well. Computed properties to the rescue!</li>
          <li>The source of all of this should be in the frontend folder of compiler-project.ast within this repo</li>
        </ul>
      <li>Backend
        <ul>
         <li>Got most of the source code ready in this repo (backend folder of compiler-project.ast), it just needs to be upgraded to the latest GraalVM, Quarkus.io needs to be used too. And cleanup the source code to only have the API calls.</li>
            <li>To finish things off: I need nginx SWAG with automated letsencrypt certificates, The rewritten VueJS frontend docker and the upgraded backend docker in a docker-compose, and everything needs to be pointing to ast-project.app domain name so I can just launch a simple VPS, copy and paste the docker compose and get things running instantly. I'll just need to login to docker one time per machine</li>
        </ul>
      </li>
      <li>Docker-slim used on the container images to optimize it, automated so if I change something it's automatically used again</li>
      <li>https://github.com/docker/docker-bench-security used for the entire CV and compiler docker compose setup to harden the entire situation</li>
      <li>The docker images live on: docker hub, a private registry on the hetzner server and the source code to build it live in this repo</li>
    </ul>
  </li>
  <li>CV - curi0sity.nl, first in simple html/css, once it's up and running, a docker so it'll fit in a docker-compose with letsencrypt in the same swag setup
    <ul>
      <li>A one-page website in html that's my resume, built with Tailwind CSS</li>
    </ul>
  </li>
  <li>Hetzner Storage box for photo's. (I'll do this)</li>
  <li>Home media server: I need a plexamp setup with redis or dragonfly cache behind it. So music is loaded instantly into memory. Or the fasted in memory cache available with its settings to about 120GB.</li> #later
  <li>Zerosec.nl (own company in a bit)</li> # later
</ul>

 ## Final step:
 A Designer to make it a unified whole. CV, Blog and compiler.
