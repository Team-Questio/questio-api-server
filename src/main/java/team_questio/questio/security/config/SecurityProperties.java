package team_questio.questio.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public record SecurityProperties(
        String loginUrl,
        String oauthAuthorizationUrl,
        String oauthRedirectUrl,
        String[] swaggerBaseUrl,
        String[] authBaseUrl
) {
}