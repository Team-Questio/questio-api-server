package team_questio.questio.security.application.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_questio.questio.user.domain.Role;

public class PrincipleDetails implements UserDetails, OAuth2User {
    @Getter
    private final Long id;
    private final String username;
    private final String password;
    private final Role role;

    private PrincipleDetails(Long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static PrincipleDetails of(Long id, String username, String password, Role role) {
        return new PrincipleDetails(id, username, password, role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
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

    public Map<String, Object> getClaims() {
        return Map.of("id", id, "username", username, "role", role.getRole());
    }

    @Override
    public String getName() {
        return username;
    }
}
