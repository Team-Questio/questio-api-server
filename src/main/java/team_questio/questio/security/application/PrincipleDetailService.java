package team_questio.questio.security.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.UserError;
import team_questio.questio.user.persistence.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> QuestioException.of(UserError.USER_NOT_FOUND));

        return PrincipleDetails.of(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
