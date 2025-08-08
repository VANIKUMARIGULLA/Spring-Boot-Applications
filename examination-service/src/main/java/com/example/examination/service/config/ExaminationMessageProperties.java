package com.example.examination.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Loads configurable success messages for examination endpoints.
 */
@Data
@Component
@ConfigurationProperties(prefix = "messages.examination")
public class ExaminationMessageProperties {
    private String created;
    private String fetchedAll;
    private String fetchedById;
    private String updated;
    private String patched;
    private String deleted;
}

