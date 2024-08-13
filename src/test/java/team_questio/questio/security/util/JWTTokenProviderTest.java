package team_questio.questio.security.util;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JWTTokenProviderTest {
    JWTTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        JWTProperties jwtProperties = new JWTProperties(
                "secret".repeat(20),
                "secret".repeat(20),
                3600000L,
                3600000L
        );
        jwtTokenProvider = new JWTTokenProvider(jwtProperties);
    }

    @Test
    void noDuplicatedTokenTest() {
        //given
        Map<String, Object> claims = Map.of("id", 1L, "username", "test", "role", "USER");

        //when
        String token1 = jwtTokenProvider.generateAccessToken(claims);
        String token2 = jwtTokenProvider.generateAccessToken(claims);

        //then
        Assertions.assertThat(token1).isNotEqualTo(token2);
    }
}