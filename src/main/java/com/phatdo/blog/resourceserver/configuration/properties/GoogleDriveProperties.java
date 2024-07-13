package com.phatdo.blog.resourceserver.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google-drive")
@Data
public class GoogleDriveProperties {
    private String credential;
    private String folderId;
}
