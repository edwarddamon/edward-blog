package com.lhamster.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "audience")
@Component
@Data
public class Audience {
    private String clientId;
    private String base64Secret;
    private String name;
    private Long expiresSecond;
}
