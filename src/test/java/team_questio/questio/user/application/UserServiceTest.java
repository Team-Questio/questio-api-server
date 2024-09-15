package team_questio.questio.user.application;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.infra.RedisUtil;
import team_questio.questio.user.application.command.SignUpCommand;
import team_questio.questio.user.domain.AccountType;
import team_questio.questio.user.domain.User;
import team_questio.questio.user.persistence.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private UserService userService;

    @DisplayName("[유저 테스트] 중복된 이메일 테스트 / normal -> normal")
    @Test
    void duplicatedEmailInNormalTest() {
        //given
        final String email = "test@test.com";
        final String password = "test";
        final User user = User.of(email, password);
        SignUpCommand command = new SignUpCommand(email, password);
        given(userRepository.findByUsername(any()))
                .willReturn(Optional.of(user));

        //when, then
        assertThatThrownBy(() -> userService.registerUser(command))
                .isInstanceOf(QuestioException.class)
                .hasMessage(AuthError.EMAIL_ALREADY_EXISTS_NORMAL.message());
    }

    @DisplayName("[유저 테스트] 중복된 이메일 테스트 / normal -> oauth(google)")
    @Test
    void duplicatedEmailInOauthTest() {
        //given
        final String email = "test@test.com";
        final String password = "test";
        final AccountType accountType = AccountType.GOOGLE;
        final User user = User.of(email, password, accountType);
        SignUpCommand command = new SignUpCommand(email, password);
        given(userRepository.findByUsername(any()))
                .willReturn(Optional.of(user));

        //when, then
        assertThatThrownBy(() -> userService.registerUser(command))
                .isInstanceOf(QuestioException.class)
                .hasMessage(AuthError.EMAIL_ALREADY_EXIST_GOOGLE.message());
    }


    @DisplayName("[유저 테스트] 가입 성공 테스트")
    @Test
    void successRegisterTest() {
        //given
        final String email = "test";
        final String password = "test";
        SignUpCommand command = new SignUpCommand(email, password);
        given(userRepository.existsByUsernameAndUserAccountType(any(), any())).willReturn(false);
        given(redisUtil.getEmailCertificationSuccessKey(any())).willReturn("test");
        given(redisUtil.getData(any(), any())).willReturn(java.util.Optional.of("test"));

        //when
        assertThatNoException().isThrownBy(() -> userService.registerUser(command));
    }
}