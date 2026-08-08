import api from './client'

const LOGIN_PATH = import.meta.env.VITE_LOGIN_PATH || '/login'

export async function login(credentials) {
  const response = await api.post(LOGIN_PATH, {
    userName: credentials.username,
    password: credentials.password
  })

  return response.data
}