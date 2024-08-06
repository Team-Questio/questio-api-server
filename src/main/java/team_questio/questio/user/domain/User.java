package team_questio.questio.user.domain;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import team_questio.questio.common.persistence.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    private String username;
    private String password;
    private Role role;

    private User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static User of(String username, String password, Role role) {
        return new User(username, password, role);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
