package team_questio.questio.security.application;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team_questio.questio.user.domain.Role;

public class PrincipleDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Role role;

    private PrincipleDetails(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static PrincipleDetails of(String username, String password, Role role) {
        return new PrincipleDetails(username, password, role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(role::getRole);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
