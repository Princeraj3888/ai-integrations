package com.careforever.config;

import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class TraccarClient {

    private final WebClient traccarWebClient;

    public String getDevices(String cookie) {

        return traccarWebClient
                .get()
                .uri("/api/devices")
                .header(HttpHeaders.COOKIE, cookie)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }

}
