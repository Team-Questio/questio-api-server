package team_questio.questio.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.PortfolioError;
import team_questio.questio.common.persistence.BaseEntity;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    private AccountType userAccountType = AccountType.NORMAL;

    @ColumnDefault("0")
    private Integer usageCount;

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private User(String username, String password, AccountType userAccountType) {
        this.username = username;
        this.password = password;
        this.userAccountType = userAccountType;
    }

    public static User of(String username, String password) {
        return new User(username, password);
    }

    public static User of(String username, String password, AccountType userAccountType) {
        return new User(username, password, userAccountType);
    }

    public Integer count(Integer quota) {
        if (this.usageCount.equals(quota)) {
            throw QuestioException.of(PortfolioError.EXCEEDED_ATTEMPTS);
        }
        this.usageCount++;

        return quota - usageCount;
    }
}
