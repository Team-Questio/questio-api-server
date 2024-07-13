package team_questio.questio.gpt.service.util.dto;

import java.util.List;

public record GPTRequest(
        String model,
        List<GPTMessage> messages
) {
    public GPTRequest(String model, String systemMessage, String content) {
        this(model, List.of(new GPTMessage("system", systemMessage), new GPTMessage("user", content)));
    }
}
