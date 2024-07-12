package team_questio.questio.gpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team_questio.questio.gpt.service.util.GPTUtil;
import team_questio.questio.gpt.service.util.dto.response.GPTResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class GPTService {
    private final GPTUtil gptUtil;

    public Long generateQuestion(String portfolio) {
        var template = new RestTemplate();
        var request = gptUtil.createHttpEntity(portfolio);

        try {
            var response =
                    template.exchange(gptUtil.getUrl(), HttpMethod.POST, request, GPTResponse.class).getBody();
        } catch (Exception e) {
            log.error("Error in generating question: " + e.getMessage());
        }
        return null;
    }
}
