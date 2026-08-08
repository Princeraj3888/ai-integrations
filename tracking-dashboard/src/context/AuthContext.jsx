import { createContext, useContext, useEffect, useMemo, useState } from 'react'
import { login as loginApi } from '../api/authApi'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('jwt'))
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('user')
    return saved ? JSON.parse(saved) : null
  })
  const [loading, setLoading] = useState(false)

  async function login(credentials) {
    setLoading(true)
    try {
      const data = await loginApi(credentials)

      // Supports common backend response shapes:
      // { token: "..." }, { accessToken: "..." }, or a raw JWT string.
      const jwt =
        data?.token ||
        data?.accessToken ||
        data?.jwt ||
        (typeof data === 'string' ? data : null)

      if (!jwt) {
        throw new Error('Login succeeded but no JWT token was found in the response.')
      }

      const loggedInUser = data?.user || data?.username
        ? (data.user || { username: data.username })
        : null

      localStorage.setItem('jwt', jwt)
      if (loggedInUser) {
        localStorage.setItem('user', JSON.stringify(loggedInUser))
      }

      setToken(jwt)
      setUser(loggedInUser)
      return data
    } finally {
      setLoading(false)
    }
  }

  function logout() {
    localStorage.removeItem('jwt')
    localStorage.removeItem('user')
    setToken(null)
    setUser(null)
  }

  useEffect(() => {
    setToken(localStorage.getItem('jwt'))
  }, [])

  const value = useMemo(
    () => ({ token, user, loading, login, logout }),
    [token, user, loading]
  )

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  return useContext(AuthContext)
}
