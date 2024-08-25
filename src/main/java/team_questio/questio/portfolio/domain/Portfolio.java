package team_questio.questio.portfolio.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.PortfolioError;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(length = 3000)
    private String content;

    public Portfolio(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public void checkOwner(final Long userId) {
        if (!this.userId.equals(userId)) {
            throw QuestioException.of(PortfolioError.PORTFOLIO_NOT_OWN);
        }
    }
}
