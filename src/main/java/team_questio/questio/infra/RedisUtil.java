package team_questio.questio.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {
    public static final String EMAIL_CERTIFICATION_PREFIX = "email_certification:";
    public static final String EMAIL_CERTIFICATION_SUCCESS_PREFIX = "email_certification:success:";
    public static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    private final RedisTemplate<String, String> redisTemplate;

    public <T> boolean setData(String key, T data, long timeout, TimeUnit timeUnit) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return false;
        }
    }

    public <T> Optional<T> getData(String key, Class<T> classType) {
        String value = (String) redisTemplate.opsForValue().get(key);
        if (value == null) {
            return Optional.empty();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.of(objectMapper.readValue(value, classType));
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public String getEmailCertificationKey(String email) {
        return EMAIL_CERTIFICATION_PREFIX.concat(email);
    }

    public String getEmailCertificationSuccessKey(String email) {
        return EMAIL_CERTIFICATION_SUCCESS_PREFIX.concat(email);
    }

    public String getRefreshTokenPrefix(String email) {
        return REFRESH_TOKEN_PREFIX.concat(email);
    }
}
