package team_questio.questio.portfolio.service.util.dto;

import java.util.List;

public record GPTRequest(
        String model,
        List<GPTMessage> messages
) {
    public GPTRequest(String model, String content) {
        this(model, List.of(new GPTMessage(content)));
    }
}
