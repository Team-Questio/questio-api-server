package team_questio.questio.gpt.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team_questio.questio.gpt.service.dto.GPTQuestionInfo;
import team_questio.questio.gpt.service.dto.GptParam;
import team_questio.questio.gpt.service.util.GPTUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class GPTService {
    private final GPTUtil gptUtil;

    public List<GPTQuestionInfo> generateQuestion(GptParam portfolio) {
        var template = new RestTemplate();
        var request = gptUtil.createHttpEntity(portfolio.portfolio());

        var response =
                template.exchange(gptUtil.getUrl(), HttpMethod.POST, request, String.class);
        if (response.getStatusCode().isError()) {
            throw new IllegalArgumentException("Failed to generate question");
        }

        return parseResponse(response.getBody());
    }

    private List<GPTQuestionInfo> parseResponse(String body) {
        JSONObject jsonObject = new JSONObject(body);
        JSONArray questions = new JSONObject(
                jsonObject.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content"))
                .getJSONArray("questions");

        List<GPTQuestionInfo> questionInfos = new ArrayList<>();
        for (int i = 0; i < questions.length(); i++) {
            questionInfos.add(
                    GPTQuestionInfo.of(
                            questions.getJSONObject(i).getString("question"),
                            questions.getJSONObject(i).getString("answer")
                    ));
        }
        return questionInfos;
    }
}
