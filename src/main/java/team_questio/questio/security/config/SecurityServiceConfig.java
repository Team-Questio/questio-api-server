package team_questio.questio.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import team_questio.questio.security.application.PrincipleDetailService;
import team_questio.questio.user.persistence.UserRepository;

@Configuration
public class SecurityServiceConfig {

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public PrincipleDetailService principleDetailService(
            DefaultOAuth2UserService defaultOAuth2UserService,
            UserRepository userRepository
    ) {
        return new PrincipleDetailService(userRepository, defaultOAuth2UserService);
    }
}
