import api from './client'

const DEVICES_PATH = import.meta.env.VITE_DEVICES_PATH || '/api/traccar/devices'
const POSITIONS_PATH = import.meta.env.VITE_POSITIONS_PATH || '/api/traccar/positions'

export async function getDevices() {
  const response = await api.get(DEVICES_PATH)
  return response.data
}

export async function getPositions() {
  const response = await api.get(POSITIONS_PATH)
  return response.data
}
