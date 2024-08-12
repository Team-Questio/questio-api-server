package team_questio.questio.user.domain;

public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
