# mattijs.id, ast-project.app, curi0sity.nl

All DNS is on cloudflare

## Infrastructure
<ul>
  <li>Blog (curi0sity.sh)</li>
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
        <li>Should be deployed on hetzner cloud instance with docker with frontend pointing to this API</li>
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
