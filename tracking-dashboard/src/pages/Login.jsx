import { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Login() {
  const navigate = useNavigate()
  const location = useLocation()
  const { login, loading } = useAuth()

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  async function handleSubmit(event) {
    event.preventDefault()
    setError('')

    try {
      await login({ username, password })
      const destination = location.state?.from?.pathname || '/dashboard'
      navigate(destination, { replace: true })
    } catch (err) {
      const message =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        'Login failed'
      setError(message)
    }
  }

  return (
    <main className="login-page">
      <section className="login-card">
        <div className="brand-mark">🚗</div>
        <h1>Vehicle Tracking</h1>
        <p className="muted">Sign in to your dashboard</p>

        <form onSubmit={handleSubmit}>
          <label>
            Username
            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              autoComplete="username"
              required
            />
          </label>

          <label>
            Password
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              autoComplete="current-password"
              required
            />
          </label>

          {error && <div className="error-box">{error}</div>}

          <button className="primary-button" disabled={loading}>
            {loading ? 'Signing in...' : 'Login'}
          </button>
        </form>
      </section>
    </main>
  )
}
