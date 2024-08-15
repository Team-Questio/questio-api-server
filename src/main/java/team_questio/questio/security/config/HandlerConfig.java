package team_questio.questio.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team_questio.questio.security.application.JWTTokenService;
import team_questio.questio.security.filter.AuthenticationResultHandler;
import team_questio.questio.security.filter.AuthorizationFailureHandler;

@Configuration
public class HandlerConfig {
    @Bean
    public AuthenticationResultHandler authenticationResultHandler(
            JWTTokenService jwtTokenService
    ) {
        return new AuthenticationResultHandler(jwtTokenService);
    }

    @Bean
    public AuthorizationFailureHandler authorizationFailureHandler() {
        return new AuthorizationFailureHandler();
    }
}
