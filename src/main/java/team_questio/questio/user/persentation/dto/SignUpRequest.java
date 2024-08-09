package team_questio.questio.user.persentation.dto;

import team_questio.questio.user.application.command.SignUpCommand;

public record SignUpRequest(
        String username,
        String password
) {
    public SignUpCommand toCommand() {
        return new SignUpCommand(username, password);
    }
}
