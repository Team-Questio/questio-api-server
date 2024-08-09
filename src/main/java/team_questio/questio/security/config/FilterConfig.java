package team_questio.questio.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import team_questio.questio.security.filter.JWTAuthenticationFilter;
import team_questio.questio.security.util.JWTTokenProvider;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JWTTokenProvider jwtTokenProvider
    ) {
        var filter = new JWTAuthenticationFilter(authenticationManager, jwtTokenProvider);
        filter.setFilterProcessesUrl(securityProperties.loginUrl());

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
