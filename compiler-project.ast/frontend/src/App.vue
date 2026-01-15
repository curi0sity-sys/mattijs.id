<script setup>
import { ref, onMounted } from 'vue'
import { listFiles, viewFile, compileFile } from './api/compiler'

const files = ref([])
const selectedFile = ref(null)
const source = ref('')
const output = ref('')
const status = ref('Idle')

async function loadFiles() {
  files.value = await listFiles()
}

async function selectFile(filename) {
  selectedFile.value = filename
  source.value = await viewFile(filename)
  output.value = ''
  status.value = 'Idle'
}

async function runCompile() {
  if (!selectedFile.value) return
  status.value = 'Compiling…'
  output.value = await compileFile(selectedFile.value)
  status.value = 'Done'
}

onMounted(loadFiles)
</script>

<template>
  <!-- ROOT -->
  <div class="fixed inset-0 overflow-x-hidden bg-gray-900 text-gray-100 flex flex-col">

    <!-- TOOLBAR -->
    <header class="h-10 flex items-center justify-between px-4 border-b border-gray-700 bg-gray-800">
      <div class="text-sm font-medium">
        Pascal → JVM Compiler
      </div>

      <div class="flex items-center gap-3">
        <span class="text-xs text-gray-400">{{ status }}</span>
        <button
          @click="runCompile"
          class="px-3 py-1 text-sm rounded bg-blue-600 hover:bg-blue-700 disabled:opacity-50"
          :disabled="!selectedFile"
        >
          ▶ Run
        </button>
      </div>
    </header>

    <!-- MAIN -->
    <div class="flex flex-1 overflow-hidden">

      <!-- FILES -->
      <aside class="w-64 flex-shrink-0 border-r border-gray-700 p-3 overflow-y-auto">
        <div class="text-xs uppercase text-gray-400 mb-3">Files</div>

        <ul class="space-y-1 text-sm">
          <li
            v-for="f in files"
            :key="f.filename"
            @click="selectFile(f.filename)"
            class="px-2 py-1 rounded cursor-pointer"
            :class="f.filename === selectedFile ? 'bg-gray-700' : 'hover:bg-gray-800'"
          >
            <div class="font-medium">{{ f.filename }}</div>
            <div class="text-xs text-gray-400">{{ f.title }}</div>
          </li>
        </ul>
      </aside>

      <!-- EDITOR -->
      <main class="flex-1 min-w-0 border-r border-gray-700 p-3 flex flex-col overflow-hidden">
        <div class="text-xs uppercase text-gray-400 mb-2">Pascal Source</div>

        <textarea
          v-model="source"
          class="flex-1 w-full bg-gray-800 text-gray-100 font-mono text-sm p-3 resize-none outline-none overflow-auto"
          spellcheck="false"
        ></textarea>
      </main>

      <!-- OUTPUT -->
      <section class="w-96 flex-shrink-0 p-3 flex flex-col overflow-hidden">
        <div class="text-xs uppercase text-gray-400 mb-2">JVM Output</div>

        <pre class="flex-1 bg-black text-green-400 font-mono text-xs p-3 overflow-auto">
{{ output || '; Click Run to compile' }}
        </pre>
      </section>

    </div>
  </div>
</template>
