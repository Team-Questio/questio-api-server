package team_questio.questio.security.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.security.application.AuthenticationService;
import team_questio.questio.security.application.JWTTokenService;
import team_questio.questio.security.presentation.dto.EmailAuthRequest;
import team_questio.questio.security.presentation.dto.TokenRefreshRequest;
import team_questio.questio.security.presentation.dto.TokenRefreshResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApiController {
    private final AuthenticationService authenticationService;
    private final JWTTokenService jwtTokenService;

    @PostMapping("/email-auth")
    @ResponseStatus(HttpStatus.OK)
    public void authenticateEmail(@RequestBody EmailAuthRequest emailAuthRequest) {
        authenticationService.sendEmail(emailAuthRequest.email());
    }

    @PostMapping("/email-auth/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEmail(@RequestBody EmailAuthRequest emailAuthRequest,
                            @RequestParam("code") String code
    ) {
        authenticationService.verifyCode(emailAuthRequest.email(), code);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        var tokenInfo = jwtTokenService.reissueAccessToken(request.refreshToken());

        var response = TokenRefreshResponse.from(tokenInfo);
        return ResponseEntity.ok()
                .body(response);
    }
}
