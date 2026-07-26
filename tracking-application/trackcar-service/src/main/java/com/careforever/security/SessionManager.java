package com.careforever.security;

import com.careforever.config.TraccarProperties;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SessionManager {

    private final WebClient traccarWebClient;
    private final TraccarProperties properties;

    private volatile String sessionCookie;

    public String getSessionCookie() {

        if (sessionCookie == null) {
            login();
        }

        return sessionCookie;
    }

    public synchronized void login() {

        LinkedMultiValueMap<String, String> form =
                new LinkedMultiValueMap<>();

        form.add("email", properties.getEmail());
        form.add("password", properties.getPassword());

        ResponseEntity<Void> response = traccarWebClient
                .post()
                .uri("/api/session")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .toBodilessEntity()
                .block();

        sessionCookie =
                response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        System.out.println("Logged into Traccar");
    }

    public synchronized void refresh() {
        login();
    }

}
