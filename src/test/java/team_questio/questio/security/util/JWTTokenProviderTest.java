package team_questio.questio.security.util;

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
        final Long id = 1L;
        final String username = "user1";

        //when
        String token1 = jwtTokenProvider.generateAccessToken(id, username);
        String token2 = jwtTokenProvider.generateAccessToken(id, username);

        //then
        Assertions.assertThat(token1).isNotEqualTo(token2);
    }
}