package team_questio.questio.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.filter.CorsFilter;
import team_questio.questio.security.application.PrincipleDetailService;
import team_questio.questio.security.filter.AuthenticationResultHandler;
import team_questio.questio.security.filter.AuthorizationFailureHandler;
import team_questio.questio.security.filter.JWTAuthorizationFilter;
import team_questio.questio.security.filter.QuestioAuthenticationEntryPointHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationResultHandler authenticationResultHandler,
            AuthorizationFailureHandler authorizationFailureHandler,
            QuestioAuthenticationEntryPointHandler questioAuthenticationEntryPointHandler,
            CorsFilter corsFilter,
            JWTAuthorizationFilter jwtAuthorizationFilter,
            PrincipleDetailService principleDetailService
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(securityProperties.authBaseUrl()).permitAll()
                .requestMatchers(securityProperties.swaggerBaseUrl()).permitAll()
                .anyRequest().authenticated()
        );

        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(authorizationFailureHandler)
                .authenticationEntryPoint(questioAuthenticationEntryPointHandler)
        );

        http.formLogin(formLogin -> formLogin
                .loginProcessingUrl(securityProperties.loginUrl())
                .successHandler(authenticationResultHandler)
                .failureHandler(authenticationResultHandler)
        );

        http.oauth2Login(oauth2Login -> oauth2Login
                .authorizationEndpoint(authorization -> authorization
                        .baseUri(securityProperties.oauthAuthorizationUrl())
                )
                .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                        .baseUri(securityProperties.oauthRedirectUrl())
                )
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(principleDetailService)
                )
                .successHandler(authenticationResultHandler)
                .failureHandler(authenticationResultHandler)
        );

        http.addFilter(corsFilter);
        http.addFilterAfter(jwtAuthorizationFilter, AuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}