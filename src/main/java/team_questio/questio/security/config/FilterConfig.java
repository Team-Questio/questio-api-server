package team_questio.questio.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team_questio.questio.security.filter.JWTAuthorizationFilter;
import team_questio.questio.security.util.JWTTokenProvider;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(
            JWTTokenProvider jwtTokenProvider
    ) {
        return new JWTAuthorizationFilter(jwtTokenProvider);
    }
}
