package com.careforever;

import com.careforever.config.TraccarProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class TrackcarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackcarServiceApplication.class, args);
	}

}
