package me.daniel.taskapi.global.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JwtMetaProperties {
    private String secret;
    private String issuer;
    private Long expireSeconds;
}
