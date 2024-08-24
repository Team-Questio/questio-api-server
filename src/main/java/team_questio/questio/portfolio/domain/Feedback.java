package team_questio.questio.portfolio.domain;

import java.util.Arrays;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.PortfolioError;

public enum Feedback {
    GOOD(1),
    NONE(0),
    BAD(-1);

    private final Integer score;

    Feedback(Integer score) {
        this.score = score;
    }

    public static Feedback of(Integer value) {
        return Arrays.stream(values())
            .filter(feedback -> feedback.score.equals(value))
            .findFirst()
            .orElseThrow(() -> QuestioException.of(PortfolioError.FEEDBACK_INVALID));
    }
}
