package team_questio.questio.feedback.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_questio.questio.common.persistence.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    private Long userId;

    @Column(length = 1000)
    private String content;

    public Feedback(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public static Feedback of(Long userId, String content) {
        return new Feedback(userId, content);
    }
}
