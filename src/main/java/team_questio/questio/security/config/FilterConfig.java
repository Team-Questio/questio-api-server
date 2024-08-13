package team_questio.questio.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import team_questio.questio.security.application.JWTTokenService;
import team_questio.questio.security.filter.JWTAuthenticationFilter;
import team_questio.questio.security.filter.JWTAuthorizationFilter;
import team_questio.questio.security.util.JWTTokenProvider;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JWTTokenService jwtTokenService
    ) {
        var filter = new JWTAuthenticationFilter(authenticationManager, jwtTokenService);
        filter.setFilterProcessesUrl(securityProperties.loginUrl());

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JWTTokenProvider jwtTokenProvider
    ) {
        return new JWTAuthorizationFilter(authenticationManager, jwtTokenProvider);
    }
}
