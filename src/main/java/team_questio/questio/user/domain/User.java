package team_questio.questio.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.PortfolioError;
import team_questio.questio.common.persistence.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    private AccountType userAccountType = AccountType.NORMAL;

    private Integer quota;

    private User(String username, String password, Integer quota) {
        this.username = username;
        this.password = password;
        this.quota = quota;
    }

    private User(String username, String password, AccountType userAccountType, Integer quota) {
        this.username = username;
        this.password = password;
        this.userAccountType = userAccountType;
        this.quota = quota;
    }

    public static User of(String username, String password, Integer quota) {
        return new User(username, password, quota);
    }

    public static User of(String username, String password, AccountType userAccountType, Integer quota) {
        return new User(username, password, userAccountType, quota);
    }

    public void deductQuota() {
        if (this.quota.equals(0)) {
            throw QuestioException.of(PortfolioError.EXCEEDED_ATTEMPT);
        }
        this.quota--;
    }
}
