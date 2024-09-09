package team_questio.questio.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.infra.RedisUtil;
import team_questio.questio.user.application.command.SignUpCommand;
import team_questio.questio.user.domain.AccountType;
import team_questio.questio.user.domain.User;
import team_questio.questio.user.persistence.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    public void registerUser(SignUpCommand command) {
        if (existUsername(command.username())) {
            throw QuestioException.of(AuthError.EMAIL_ALREADY_EXISTS);
        }

        verifyEmailCertified(command.username());
        var encodedPassword = passwordEncoder.encode(command.password());
        var user = User.of(command.username(), encodedPassword);
        userRepository.save(user);
    }

    public Integer deductQuota(Long id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> QuestioException.of(AuthError.USER_NOT_FOUND));

        user.deductQuota();
        return user.getQuota();
    }

    public Integer getQuota(Long id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> QuestioException.of(AuthError.USER_NOT_FOUND));
        return user.getQuota();
    }

    private boolean existUsername(final String username) {
        return userRepository.existsByUsernameAndUserAccountType(username, AccountType.NORMAL);
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
