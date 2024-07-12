package team_questio.questio.portfolio.service.util;


import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import team_questio.questio.portfolio.service.util.dto.GPTRequest;

@Component
public class GPTUtil {
    @Value("${openai.secretKey}")
    private String secretKey;
    @Getter
    @Value("${openai.url}")
    private String url;
    @Value("${openai.model}")
    private String model;

    public HttpEntity<GPTRequest> createHttpEntity(String content) {
        return new HttpEntity<>(createGPTRequest(content), getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }

    private GPTRequest createGPTRequest(String content) {
        return new GPTRequest(model, content);
    }

}
