package team_questio.questio.security.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.user.domain.AccountType;
import team_questio.questio.user.domain.User;
import team_questio.questio.user.persistence.UserRepository;

@ExtendWith(MockitoExtension.class)
class PrincipleDetailServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OAuth2UserRequest oAuth2UserRequest;

    @Mock
    private OAuth2User oAuth2User;

    @Mock
    private ClientRegistration clientRegistration;

    @Mock
    private DefaultOAuth2UserService defaultOAuth2UserService;

    @InjectMocks
    private PrincipleDetailService principleDetailService;

    @Test
    @DisplayName("[Oauth2 회원가입 테스트] 중복된 이메일")
    void registerUserWithDuplicatedEmailTest() {
        final String oauthServer = "google";
        final String emailKey = "email";

        final String email = "test@test.com";
        final String password = "test";

        final User user = User.of(email, password);

        // DefaultOAuth2UserService.loadUser()에서 호출되는 메서드
        when(oAuth2UserRequest.getClientRegistration())
                .thenReturn(clientRegistration);
        when(oAuth2UserRequest.getClientRegistration().getRegistrationId())
                .thenReturn(oauthServer);
        when(defaultOAuth2UserService.loadUser(oAuth2UserRequest))
                .thenReturn(oAuth2User);

        when(oAuth2User.getAttributes())
                .thenReturn(Map.of(emailKey, email));
        when(userRepository.existsByUsernameAndUserAccountType(email, AccountType.GOOGLE))
                .thenReturn(false);
        when(userRepository.findByUsername(email))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(() -> principleDetailService.loadUser(oAuth2UserRequest))
                .isInstanceOf(QuestioException.class)
                .hasMessage(AuthError.EMAIL_ALREADY_EXISTS_NORMAL.message());
    }
}