package team_questio.questio.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.infra.RedisUtil;
import team_questio.questio.user.application.command.SignUpCommand;
import team_questio.questio.user.domain.User;
import team_questio.questio.user.persistence.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Value("${portfolio.quota}")
    private Integer quota;

    public void registerUser(SignUpCommand command) {
        checkUsernameDuplicated(command.username());

        verifyEmailCertified(command.username());
        var encodedPassword = passwordEncoder.encode(command.password());
        var user = User.of(command.username(), encodedPassword);
        userRepository.save(user);
    }

    public Integer countRemaining(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> QuestioException.of(AuthError.USER_NOT_FOUND));
        return user.countRemaining(quota);
    }

    private void checkUsernameDuplicated(final String username) {
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

    private void verifyEmailCertified(String username) {
        var key = redisUtil.getEmailCertificationSuccessKey(username);
        redisUtil.getData(key, String.class)
                .orElseThrow(() -> QuestioException.of(AuthError.CERTIFICATION_INFO_NOT_FOUND));
        redisUtil.deleteData(key);
    }


    public String getUsername(final Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> QuestioException.of(AuthError.USER_NOT_FOUND));

        return user.getUsername();
    }

}
