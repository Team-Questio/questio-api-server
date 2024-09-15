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

    private Integer quota = 5;

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

    public void deductQuota() {
        if (this.quota.equals(0)) {
            throw QuestioException.of(PortfolioError.EXCEEDED_ATTEMPT);
        }
        this.quota--;
    }

    public boolean isKakaoUser() {
        return AccountType.KAKAO.equals(this.userAccountType);
    }

    public boolean isGoogleUser() {
        return AccountType.GOOGLE.equals(this.userAccountType);
    }

    public boolean isNaverUser() {
        return AccountType.NAVER.equals(this.userAccountType);
    }

    public boolean isNormalUser() {
        return AccountType.NORMAL.equals(this.userAccountType);
    }

}
