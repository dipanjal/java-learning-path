package com.example.restapi.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
@ConfigurationProperties("application")
@Getter
@Setter
public class AppProperties {
    private String requestIdHeader;
    private int tokenExpiryMinute;
    private String tokenPrefix;
    private String jwtSecret;
    private String authHeaderName;
    private String tokenValidationRegex;
    private boolean seederEnabled;
    private String apiVersion;
}
