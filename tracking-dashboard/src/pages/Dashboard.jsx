import { useEffect, useMemo, useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { getDevices, getPositions } from '../api/traccarApi'
import VehicleMap from '../components/VehicleMap'

function normalizeArray(data) {
  if (Array.isArray(data)) return data
  if (Array.isArray(data?.data)) return data.data
  if (Array.isArray(data?.content)) return data.content
  return []
}

export default function Dashboard() {
  const { user, logout } = useAuth()
  const [devices, setDevices] = useState([])
  const [positions, setPositions] = useState([])
  const [selectedDeviceId, setSelectedDeviceId] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  async function loadData() {
    try {
      setError('')
      const [deviceData, positionData] = await Promise.all([
        getDevices(),
        getPositions()
      ])

      const nextDevices = normalizeArray(deviceData)
      const nextPositions = normalizeArray(positionData)

      setDevices(nextDevices)
      setPositions(nextPositions)

      if (!selectedDeviceId && nextDevices.length) {
        setSelectedDeviceId(nextDevices[0].id)
      }
    } catch (err) {
      setError(
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        'Unable to load tracking data'
      )
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadData()
    const timer = setInterval(loadData, 10000)
    return () => clearInterval(timer)
  }, [])

  const positionByDevice = useMemo(() => {
    return positions.reduce((map, position) => {
      map[position.deviceId] = position
      return map
    }, {})
  }, [positions])

  const selectedPosition = selectedDeviceId
    ? positionByDevice[selectedDeviceId]
    : positions[0]

  return (
    <div className="app-shell">
      <header className="topbar">
        <div>
          <strong>🚗 Vehicle Tracking</strong>
          <span className="topbar-subtitle">Dashboard</span>
        </div>
        <div className="topbar-actions">
          <span>{user?.username || 'User'}</span>
          <button className="logout-button" onClick={logout}>Logout</button>
        </div>
      </header>

      <div className="dashboard-grid">
        <aside className="sidebar">
          <div className="sidebar-title">
            <span>Vehicles</span>
            <span className="count">{devices.length}</span>
          </div>

          {loading && <p className="muted">Loading...</p>}
          {error && <div className="error-box">{error}</div>}

          <div className="vehicle-list">
            {devices.map((device) => {
              const position = positionByDevice[device.id]
              const online = Boolean(position?.valid)

              return (
                <button
                  key={device.id}
                  className={`vehicle-item ${selectedDeviceId === device.id ? 'selected' : ''}`}
                  onClick={() => setSelectedDeviceId(device.id)}
                >
                  <span className={`status-dot ${online ? 'online' : 'offline'}`} />
                  <span className="vehicle-info">
                    <strong>{device.name || `Device ${device.id}`}</strong>
                    <small>{device.uniqueId || 'No unique ID'}</small>
                  </span>
                  <span className="vehicle-speed">
                    {position?.speed != null ? `${Number(position.speed).toFixed(0)} km/h` : '--'}
                  </span>
                </button>
              )
            })}

            {!loading && devices.length === 0 && (
              <p className="muted">No vehicles found.</p>
            )}
          </div>
        </aside>

        <main className="content">
          <section className="map-card">
            <VehicleMap
              devices={devices}
              positions={positions}
              selectedDeviceId={selectedDeviceId}
              onSelect={setSelectedDeviceId}
            />
          </section>

          <section className="details-card">
            <div>
              <span className="label">Vehicle</span>
              <strong>
                {devices.find((d) => d.id === selectedDeviceId)?.name || '--'}
              </strong>
            </div>
            <div>
              <span className="label">Speed</span>
              <strong>{selectedPosition ? `${Number(selectedPosition.speed || 0).toFixed(0)} km/h` : '--'}</strong>
            </div>
            <div>
              <span className="label">Ignition</span>
              <strong>{selectedPosition?.attributes?.ignition ? 'ON' : 'OFF'}</strong>
            </div>
            <div>
              <span className="label">Battery</span>
              <strong>{selectedPosition?.attributes?.battery != null ? `${selectedPosition.attributes.battery} V` : '--'}</strong>
            </div>
            <div>
              <span className="label">GPS</span>
              <strong>
                {selectedPosition
                  ? `${selectedPosition.latitude.toFixed(5)}, ${selectedPosition.longitude.toFixed(5)}`
                  : '--'}
              </strong>
            </div>
            <div>
              <span className="label">Last Fix</span>
              <strong>{selectedPosition?.fixTime ? new Date(selectedPosition.fixTime).toLocaleString() : '--'}</strong>
            </div>
          </section>
        </main>
      </div>
    </div>
  )
}
