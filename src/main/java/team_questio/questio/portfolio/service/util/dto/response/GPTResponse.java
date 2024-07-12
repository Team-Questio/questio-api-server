package team_questio.questio.portfolio.service.util.dto.response;

import java.util.List;

public record GPTResponse(
        List<GPTChoice> choices
) {
}
