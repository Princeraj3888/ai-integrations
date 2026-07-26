package com.careforever.client;

import com.careforever.dto.DeviceDto;
import com.careforever.security.SessionManager;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class TraccarClient {

    private final WebClient traccarWebClient;
    private final SessionManager sessionManager;

    public List<DeviceDto> getDevices() {

        return Optional.ofNullable(
                execute(() ->
                        traccarWebClient
                                .get()
                                .uri("/api/devices")
                                .header(HttpHeaders.COOKIE,
                                        sessionManager.getSessionCookie())
                                .retrieve()
                                .bodyToFlux(DeviceDto.class)
                                .collectList()
                                .block()))
            .orElse(List.of());
    }

    public String getPositions() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/positions")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getEvents() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/events")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getGeofences() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/geofences")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getDrivers() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/drivers")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getGroups() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/groups")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getRoute() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/reports/route")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getTrips() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/reports/trips")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    public String getStops() {

        return executeString(() ->
                traccarWebClient
                        .get()
                        .uri("/api/reports/stops")
                        .header(HttpHeaders.COOKIE,
                                sessionManager.getSessionCookie())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block());

    }

    private <T> T execute(Supplier<T> supplier) {

        try {
            return supplier.get();
        } catch (WebClientResponseException.Unauthorized ex) {

            sessionManager.refresh();

            return supplier.get();
        }
    }

    private String executeString(Supplier<String> supplier) {

        try {
            return supplier.get();
        }
        catch (WebClientResponseException.Unauthorized ex) {

            sessionManager.refresh();

            return supplier.get();
        }

    }

}
