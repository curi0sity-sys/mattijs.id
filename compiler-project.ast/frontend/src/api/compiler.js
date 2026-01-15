const BASE_URL = '/api'

export async function listFiles() {
  const res = await fetch(`${BASE_URL}/list`)
  if (!res.ok) throw new Error('Failed to list files')
  return res.json()
}

export async function viewFile(name) {
  const res = await fetch(`${BASE_URL}/view/${name}`)
  if (!res.ok) throw new Error('Failed to load file')
  return res.text()
}

export async function compileFile(name) {
  const res = await fetch(`${BASE_URL}/compile/${name}`, { method: 'POST' })
  if (!res.ok) throw new Error('Failed to compile')
  return res.text()
}
