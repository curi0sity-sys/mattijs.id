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
    Compiler (ast-project.app) - most of this project's source-code is 90% done. Just needs a docker-compose .
    <ul>
      <li>Frontend</li>
        <ul>
           <li>The old frontend has been found and source code is available in 'mattijs-editor' folder with node 11 it runs decently</li>
           <li>The source of all of this should be in the frontend folder of compiler-project.ast within this repo with all duplicate stuff removed.</li>
            <li>Hosted on cloudflare pages via vuejs production dist folder.</li>
        </ul>
      <li>Backend
        <ul>
         <li>Got most of the source code ready in this repo (backend folder of compiler-project.ast), The latest graalvm checkups should be done. And cleanup the source code to only have the API calls.</li>
        <li>Should be deployed on AWS Fargate with frontend pointing to this API</li>
        </ul>
      </li>
    </ul>
  </li>
  <li>CV - mattijs.id
    <ul>
      <li>A one-page website in html that's my resume. Hosted on cloudflare pages </li>
    </ul>
  </li>
</ul>

 ## Final step:
 A Designer to make it a unified whole. CV, Blog, compiler.
Spellcheck on all websites.