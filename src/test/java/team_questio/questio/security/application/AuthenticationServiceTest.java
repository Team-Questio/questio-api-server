package team_questio.questio.security.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test", "dev"})
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void sendMessage(@Value("${test.email}") String email) {
        authenticationService.sendEmail(email);
    }
}