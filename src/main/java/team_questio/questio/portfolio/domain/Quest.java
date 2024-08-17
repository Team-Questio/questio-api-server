package team_questio.questio.portfolio.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;
    private Integer feedback;

    private Long portfolioId;

    @Builder
    public Quest(String question, String answer, Long portfolioId) {
        this.question = question;
        this.answer = answer;
        this.portfolioId = portfolioId;
    }

    public void updateFeedback(Integer feedback) {
        this.feedback = feedback;
    }
}
