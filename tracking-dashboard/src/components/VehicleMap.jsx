import { useEffect } from 'react'
import { MapContainer, Marker, Popup, TileLayer, useMap } from 'react-leaflet'
import L from 'leaflet'

const markerIcon = new L.Icon({
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
})

function MapCenter({ position }) {
  const map = useMap()

  useEffect(() => {
    if (position?.latitude != null && position?.longitude != null) {
      map.setView([position.latitude, position.longitude], Math.max(map.getZoom(), 14))
    }
  }, [position, map])

  return null
}

export default function VehicleMap({ devices, positions, selectedDeviceId, onSelect }) {
  const validPositions = positions.filter(
    (p) => Number.isFinite(p.latitude) && Number.isFinite(p.longitude)
  )

  const selected = validPositions.find((p) => p.deviceId === selectedDeviceId)
  const center = selected
    ? [selected.latitude, selected.longitude]
    : validPositions.length
      ? [validPositions[0].latitude, validPositions[0].longitude]
      : [17.5407383, 78.4272583]

  return (
    <MapContainer center={center} zoom={14} className="map">
      <TileLayer
        attribution='&copy; OpenStreetMap contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />

      <MapCenter position={selected} />

      {validPositions.map((position) => {
        const device = devices.find((d) => d.id === position.deviceId)

        return (
          <Marker
            key={position.deviceId}
            position={[position.latitude, position.longitude]}
            icon={markerIcon}
            eventHandlers={{
              click: () => onSelect(position.deviceId)
            }}
          >
            <Popup>
              <strong>{device?.name || `Device ${position.deviceId}`}</strong>
              <br />
              Speed: {Number(position.speed || 0).toFixed(0)} km/h
              <br />
              Ignition: {position.attributes?.ignition ? 'ON' : 'OFF'}
              <br />
              Battery: {position.attributes?.battery ?? '--'} V
            </Popup>
          </Marker>
        )
      })}
    </MapContainer>
  )
}
