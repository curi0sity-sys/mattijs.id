# mattijs.id, ast-project.app, curi0sity.nl

## Setup
<ul>
  <li>Setup a k8s cluster for all 3 projects below and include sticky sessions for the compiler. All 3 projects will live on Google K8S</li>
  <li>The migration that'll need to happen is pretty much for emails only. The DNS currently lives on hosting.nl which will need to be migrated to the same Google Cloud account</li>
</ul>

## Infrastructure
<ul>
  <li>Blog (mattijs.id)
    <ul>
      <li>bunnyhopcdn all data + migration of current (done with magicpages) to a ghost blog setup with letsencrypt certs and migration of all current data currently on magicpages to this setup. This all will live on the same Google K8s Cluster</li>
      <li>Infographics x2, jobs already posted on toptal</li>
    </ul>
  </li>
  <li>
    Compiler (compiler-project.ast) - most of this project's source-code is 90% done. Just needs a docker-compose setup with letsencrypt certs and wiring the api calls to the frontend.
    <ul>
      <li>Frontend</li>
        <ul>
           <li>VueJS 3 frontend: left column list of whitelisted files (20% screensize). To the right of that a Pascal code view of the given file (40%), and the column next to it the JVM code (40%), I need a simple config file for the column sizes, fonts and colours. It needs to be desktop friendly, resizeable from 800x600 to 4k, we can skip a mobile view</li>
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
      <li>The docker images live on: docker hub, a registry on the google cloud and the source code to build it live in this repo</li>
    </ul>
  </li>
  <li>CV - curi0sity.nl
    <ul>
      <li>A one-page website in html that's my resume, built with Tailwind CSS. Once the looks and contents are there, It'll need to be put into a Docker so it can be put into the same K8s cluster</li>
    </ul>
  </li>
  <li>Home media server: I need a plexamp setup with redis or dragonfly cache behind it. So music is loaded instantly into memory. Or the fasted in memory cache available with its settings to about 120GB.</li> #later
  <li>Zerosec.nl (own company in a bit) A single page website which has yet to be made, will be put into a docker and live in the same K8s cluster.</li> # later. 
</ul>

 ## Final step:
 A Designer to make it a unified whole. CV, Blog and compiler.
