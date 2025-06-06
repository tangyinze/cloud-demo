package com.tyz.rabbitmq.config.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: cloud-demo
 * @description: jwt config
 * @author: tyz
 * @create: 2025-05-16
 */
@ConfigurationProperties(prefix = "jwt")
@ConditionalOnProperty(prefix = "jwt", name = "enabled", havingValue = "true")
public class CustomJwtConfig {
    private String secret;
    private Long expiration;
    private String subject;
    private String issuer;

    private boolean enabled;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "CustomJwtConfig{" +
                "secret='" + secret + '\'' +
                ", expiration=" + expiration +
                ", subject='" + subject + '\'' +
                ", issuer='" + issuer + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
