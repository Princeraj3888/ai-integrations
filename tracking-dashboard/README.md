# Tracking Dashboard

React + Vite dashboard for the vehicle-tracking backend.

## Flow

Browser
-> React
-> API Gateway (`http://localhost:8765`)
-> JWT authentication
-> Traccar integration APIs
-> Google Cloud Traccar / FMC920

## 1. Install

```bash
npm install
```

## 2. Configure

Copy `.env.example` to `.env`:

```bash
VITE_API_BASE_URL=http://localhost:8765
VITE_LOGIN_PATH=/login
VITE_DEVICES_PATH=/api/traccar/devices
VITE_POSITIONS_PATH=/api/traccar/positions
```

If your existing login endpoint or JSON contract is different, change `VITE_LOGIN_PATH` or update `src/api/authApi.js`.

The login page sends:

```json
{
  "username": "your-user",
  "password": "your-password"
}
```

The JWT response parser accepts:

```json
{ "token": "..." }
```

or

```json
{ "accessToken": "..." }
```

or

```json
{ "jwt": "..." }
```

or a raw JWT string.

## 3. Start

```bash
npm run dev
```

Open:

```text
http://localhost:5173
```

## Current behavior

- Login calls the configured backend login API.
- JWT is stored in localStorage.
- Axios automatically adds `Authorization: Bearer <JWT>`.
- Protected dashboard route redirects to `/login` when there is no token.
- 401 responses clear the token and return to login.
- Dashboard loads devices and positions.
- Dashboard refreshes tracking data every 10 seconds.
- Leaflet/OpenStreetMap displays the latest positions.

## Next

Add vehicle business APIs, history/replay, events, geofences, notifications and then replace polling with WebSocket when the end-to-end product is stable.
