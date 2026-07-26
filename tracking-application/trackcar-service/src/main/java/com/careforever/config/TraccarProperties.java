package com.careforever.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "traccar")
@Getter
@Setter
public class TraccarProperties {

    private String url;
    private String email;
    private String password;

}
