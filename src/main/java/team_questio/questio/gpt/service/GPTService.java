package team_questio.questio.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.GPTError;
import team_questio.questio.gpt.service.dto.GPTQuestionInfo;
import team_questio.questio.gpt.service.dto.GptParam;
import team_questio.questio.gpt.service.util.GPTUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class GPTService {
    private final GPTUtil gptUtil;
    private final ObjectMapper objectMapper;

    public List<GPTQuestionInfo> generateQuestion(GptParam portfolio) {
        var template = new RestTemplate();
        var request = gptUtil.createHttpEntity(portfolio.portfolio());

        var response =
                template.exchange(gptUtil.getUrl(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode().isError()) {
            throw QuestioException.of(GPTError.PORTFOLIO_GENERATE_ERROR);
        }
        log.info(response.getBody());
        return parseResponse(response.getBody());
    }

    private List<GPTQuestionInfo> parseResponse(String body) {
        try {
            JsonNode rootNode = parseContents(body);

            if (isError(rootNode)) {
                throw QuestioException.of(GPTError.PORTFOLIO_WRONG_SUBJECT);
            }

            var questionsNode = parseQuestions(rootNode);
            return parseQuestionAndAnswer(questionsNode);
        } catch (JsonProcessingException e) {
            log.warn("Json 파싱 에러: {}", e.getMessage());
            throw QuestioException.of(GPTError.PORTFOLIO_JSON_PARSE_ERROR);
        }
    }

    private JsonNode parseContents(String body) throws JsonProcessingException {
        return objectMapper.readTree(body).get("choices")
                .get(0)
                .get("message")
                .get("content");
    }

    private boolean isError(JsonNode rootNode) {
        return rootNode.asText().contains("Error") || rootNode.asText().contains("error");
    }

    private JsonNode parseQuestions(JsonNode rootNode) throws JsonProcessingException {
        String jsonString = rootNode.asText().replace("```json", "").replace("```", "").trim();

        var questionsNode = objectMapper.readTree(jsonString);

        return questionsNode.get("questions");
    }

    private List<GPTQuestionInfo> parseQuestionAndAnswer(JsonNode questions) {
        List<GPTQuestionInfo> questionInfos = new ArrayList<>();

        for (int i = 0; i < Math.min(questions.size(), 10); i++) {
            questionInfos.add(
                    GPTQuestionInfo.of(
                            questions.get(i).get("question").asText(),
                            questions.get(i).get("answer").asText()
                    ));
        }
        return questionInfos;
    }
}
