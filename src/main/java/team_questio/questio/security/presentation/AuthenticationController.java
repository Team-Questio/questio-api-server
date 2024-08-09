package team_questio.questio.security.presentation;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.security.application.AuthenticationService;
import team_questio.questio.security.presentation.dto.EmailAuthRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/email-auth")
    @ResponseStatus(HttpStatus.OK)
    public void authenticateEmail(@RequestBody EmailAuthRequest emailAuthRequest) {
        authenticationService.sendEmail(emailAuthRequest.email());
    }

    @PostMapping("/email-auth/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEmail(@RequestBody EmailAuthRequest emailAuthRequest,
                            @PathParam("code") String code
    ) {
        authenticationService.verifyCode(emailAuthRequest.email(), code);
    }
}
