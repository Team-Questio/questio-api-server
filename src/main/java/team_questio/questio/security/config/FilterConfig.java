package team_questio.questio.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import team_questio.questio.security.filter.AuthenticationResultHandler;
import team_questio.questio.security.filter.JWTAuthenticationFilter;
import team_questio.questio.security.filter.JWTAuthorizationFilter;
import team_questio.questio.security.util.JWTTokenProvider;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JWTTokenProvider jwtTokenProvider
    ) {
        return new JWTAuthorizationFilter(authenticationManager, jwtTokenProvider);
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationResultHandler authenticationResultHandler,
            AuthenticationManager authenticationManager
    ) {
        var filter = new JWTAuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl(securityProperties.loginUrl());
        filter.setAuthenticationSuccessHandler(authenticationResultHandler);
        filter.setAuthenticationFailureHandler(authenticationResultHandler);

        return new JWTAuthenticationFilter(authenticationManager);
    }
}
