package team_questio.questio.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.filter.CorsFilter;
import team_questio.questio.security.filter.JWTAuthenticationFilter;
import team_questio.questio.security.filter.JWTAuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CorsFilter corsFilter,
            JWTAuthenticationFilter jwtAuthenticationFilter,
            JWTAuthorizationFilter jwtAuthorizationFilter
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(securityProperties.authBaseUrl()).permitAll()
                        .requestMatchers(securityProperties.swaggerBaseUrl()).permitAll()
                        .anyRequest().authenticated()
        );

        http.addFilter(corsFilter);
        http.addFilterBefore(jwtAuthenticationFilter, JWTAuthorizationFilter.class);
        http.addFilterBefore(jwtAuthorizationFilter, AuthorizationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}