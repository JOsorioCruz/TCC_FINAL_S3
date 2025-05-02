package edu.unicartagena.tcc.login.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Primary
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private long expiration;
}
