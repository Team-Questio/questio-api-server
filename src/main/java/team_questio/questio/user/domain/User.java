package team_questio.questio.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
