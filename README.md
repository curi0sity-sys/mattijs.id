# mattijs.id, ast-project.app, curi0sity.nl

## Infrastructure
<ul>
  <li>Blog (curi0sity.nl)
    <ul>
      <li>bunnyhopcdn all data + migration of current (done with magicpages) to a ghost blog setup with ssl certs and migration of all current data currently on magicpages to this setup. This all will live on the same Google K8s Cluster</li>
      <li>Infographics x2, jobs already posted on toptal</li>
      <li>A designer to make the blogpost contents wider. I'm thinking 1024 or 1200 something wide</li>
    </ul>
  </li>
  <li>
    Compiler (compiler-project.ast) - most of this project's source-code is 90% done. Just needs a docker-compose setup with letsencrypt certs and wiring the api calls to the frontend. It'll be hosted on a hetzner instance.
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
    </ul>
  </li>
  <li>CV - mattijs.id
    <ul>
      <li>A one-page website in html that's my resume. Once ready, hosted on a s3 bucket via cloudfront and their certs</li>
    </ul>
  </li>
  <li>Home media server: I need a plexamp setup with redis or dragonfly cache behind it. So music is loaded instantly into memory. Or the fasted in memory cache available with its settings to about 120GB. (This won't live on the same k8s cluster</li>
</ul>

 ## Final step:
 A Designer to make it a unified whole. CV, Blog - which just means adjusting the blog colours slightly to match the CV theme.
