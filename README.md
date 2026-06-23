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
    Compiler (ast-project.app) - most of this project's source-code is 90% done.
    <ul>
      <li>Frontend</li>
        <ul>
           <li>The old frontend has been found and source code is available in 'mattijs-editor' folder with node 11 it runs decently</li>
           <li>The source of all of this should be in the frontend folder of compiler-project.ast within this repo with all duplicate stuff removed.</li>
            <li>Hosted on cloudflare pages via vuejs production dist folder.</li>
        </ul>
      <li>Backend
        <ul>
         <li>Got most of the source code ready in this repo (backend folder of compiler-project.ast).</li>
        <li>-1. editor content top left = selected file (response /rest/show/) Uncompiled pascal source file
-   2. jvm top right= response van /rest/compile/{filename} - JVM output in native jvm language
-   3. output bottom left = response van /rest/compile/{filename} - Output of file itself
-   4. xref bottom right = x reference (response van /rest/compile/{filename}) - Output of xref of this program</li>
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