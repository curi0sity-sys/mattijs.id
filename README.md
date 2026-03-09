# mattijs.id, ast-project.app, curi0sity.nl

All DNS is on cloudflare

## Infrastructure
<ul>
  <li>Blog (curi0sity.nl) - Currently on magicpages and will remain there for now
    <ul>
      <li>Infographics x2, jobs to be posted somewhere</li>
      <li>A designer to make the blogpost contents wider. I'm thinking 1024 or 1200 something wide, and responsive. End product should be a theme.zip file I can upload to magicpages</li>
    </ul>
  </li>
  <li>
    Compiler (ast-project.app) - most of this project's source-code is 90% done. Just needs a docker-compose setup with letsencrypt certs and wiring the api calls to the frontend. It'll be hosted on a hetzner vps.
    <ul>
      <li>Frontend</li>
        <ul>
           <li>The old frontend has been found and source code is available in 'mattijs-editor' folder with node 11 it runs decently</li>
          <li>The source of all of this should be in the frontend folder of compiler-project.ast within this repo with all duplicate stuff removed.</li>
        </ul>
      <li>Backend
        <ul>
         <li>Got most of the source code ready in this repo (backend folder of compiler-project.ast), it just needs to be upgraded to the latest GraalVM, Quarkus.io needs to be used too. And cleanup the source code to only have the API calls.</li>
            <li>To finish things off: I need nginx SWAG with automated letsencrypt certificates, The rewritten VueJS frontend docker and the upgraded backend docker in a docker-compose, and everything needs to be pointing to ast-project.app domain name so I can just launch a simple VPS, copy and paste the docker compose and get things running instantly. I'll just need to login to docker one time per machine</li>
        </ul>
      </li>
        <li>
               <p>See github  repo
               This will live on a completely hardend Ubuntu linux os that can only run the given compiler project.
               everything else is hard shut down for hetzner in this case.
               The DNS lives on clouddlare, for now It'll live on a single simple hetnzer vps which I'll buy. Later we'll need sticky sessions for k8s delivery.</p>
              <p>This final step can be delayed for now</p>
        </li>
    </ul>
  </li>
  <li>CV - mattijs.id
    <ul>
      <li>A one-page website in html that's my resume. with letsencrypt swag setup on a hetzner vps</li>
    </ul>
  </li>
</ul>

 ## Final step:
 A Designer to make it a unified whole. CV, Blog, copmiler -  which just means adjusting the blog colours slightly to match the CV theme.
Spellcheck on all websites.

