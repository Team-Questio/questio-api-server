package team_questio.questio.security.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.security.application.dto.PrincipleDetails;
import team_questio.questio.user.domain.AccountType;
import team_questio.questio.user.domain.User;
import team_questio.questio.user.persistence.UserRepository;

@Slf4j
@RequiredArgsConstructor
public class PrincipleDetailService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>, UserDetailsService {
    private final UserRepository userRepository;
    private final DefaultOAuth2UserService defaultOAuth2UserService;

    /*
     * This method is used to normal user login.
     */
    @Override
    public PrincipleDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameAndUserAccountType(username, AccountType.NORMAL)
                .orElseThrow(() -> QuestioException.of(AuthError.USER_NOT_FOUND));
        List<Integer> list = new ArrayList<>();

        return PrincipleDetails.of(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    /*
     * This method is used to oauth user login.
     */
    @Override
    public PrincipleDetails loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        var registration = AccountType.of(userRequest.getClientRegistration().getRegistrationId());
        var email = extractEmail(oAuth2User, registration);

        if (isNotExistUserWithOauth(email, registration)) {
            checkDuplicatedUsername(email);
            registerOAuthUser(email, registration);
        }
        var user = userRepository.findByUsernameAndUserAccountType(email, registration)
                .orElseThrow(() -> QuestioException.of(AuthError.USER_NOT_FOUND));

        return PrincipleDetails.of(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    private String extractEmail(final OAuth2User user, AccountType registration) {
        if (registration.equals(AccountType.KAKAO)) {
            return getKakaoEmail(user.getAttributes());
        }
        if (registration.equals(AccountType.GOOGLE)) {
            return getGoogleEmail(user.getAttributes());
        }
        if (registration.equals(AccountType.NAVER)) {
            return getNaverEmail(user.getAttributes());
        }

        throw new IllegalArgumentException("Unsupported registrationId: " + registration);
    }

    private String getKakaoEmail(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    public String getGoogleEmail(Map<String, Object> attributes) {
        return (String) attributes.get("email");
    }

    public String getNaverEmail(Map<String, Object> attributes) {
        Map<String, Object> response = (Map) attributes.get("response");
        return (String) response.get("email");
    }

    private boolean isNotExistUserWithOauth(String username, AccountType registration) {
        return !userRepository.existsByUsernameAndUserAccountType(username, registration);
    }

    private void checkDuplicatedUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    if (user.isNormalUser()) {
                        throw QuestioException.of(AuthError.EMAIL_ALREADY_EXISTS_NORMAL);
                    }
                    if (user.isKakaoUser()) {
                        throw QuestioException.of(AuthError.EMAIL_ALREADY_EXISTS_KAKAO);
                    }
                    if (user.isNaverUser()) {
                        throw QuestioException.of(AuthError.EMAIL_ALREADY_EXIST_NAVER);
                    }
                    if (user.isGoogleUser()) {
                        throw QuestioException.of(AuthError.EMAIL_ALREADY_EXIST_GOOGLE);
                    }
                });
    }

    private void registerOAuthUser(String username, AccountType registration) {
        var password = RandomStringUtils.random(20);
        var user = User.of(username, password, registration);
        userRepository.save(user);
    }
}
