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
    private Role role;

    private User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static User of(String username, String password) {
        return new User(username, password, Role.USER);
    }
}
