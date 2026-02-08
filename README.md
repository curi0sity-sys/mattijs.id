# mattijs.id, ast-project.app, curi0sity.nl, zerosec.nl

All DNS is on cloudflare

## Infrastructure
<ul>
  <li>Blog (curi0sity.nl) - Currently on magicpages and will remain there for now
    <ul>
      <li>Infographics x2, jobs already posted on toptal</li>
      <li>A designer to make the blogpost contents wider. I'm thinking 1024 or 1200 something wide, and responsive</li>
    </ul>
  </li>
  <li>
    Compiler (ast-project.app) - most of this project's source-code is 90% done. Just needs a docker-compose setup with letsencrypt certs and wiring the api calls to the frontend. It'll be hosted on a hosting.nl vps.
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
        <li>
               See github  repo

               This will live on a completely hardend Ubuntu linux os that can only run the given compiler project.
               everything else is hard shut down for hetzner in this case.
               The DNS lives on clouddlare, and it'll be hosted on 3 sizeable k8s machiens which will form a k8s cluster.
               It'll need sticky sessions and a small cleanup of the github repo considering there's now 2 github repo's.
            
               Also a small manual on how to keep it updated. I'm guessing a yaourt -Syua should do?
        </li>
    </ul>
  </li>
  <li>CV - mattijs.id
    <ul>
      <li>A one-page website in html that's my resume. Hosted on simple hosting package with ssl cert from hosting.nl </li>
    </ul>
  </li>
<li>Zerosec.nl - Company website, simple one-pager. Website is there, just need a small design job. Hosted on simple hosting package with ssl cert from hosting.nl</li>
<li>Storage systeem: rsync.net account to store everything, which automatically get's more storage. Buy this this week</li>
</ul>

 ## Final step:
 A Designer to make it a unified whole. CV, Blog, copmiler, zerosec- which just means adjusting the blog colours slightly to match the CV theme.
Spellcheck on all websites.

Need the docker signing security step on my docker containers as well. (Add this to resume as well)

All of this on 3 simple hetzner servers I'll buy in a docker-compose-setup.

Once that outscales, I'll buy 6 more servers and go for k8s on hetzner.


And if that ever reaches a limit,  I'll just buy more hetzner servers.
