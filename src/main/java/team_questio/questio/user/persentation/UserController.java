package team_questio.questio.user.persentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.user.application.UserService;
import team_questio.questio.user.persentation.dto.RemainingResponse;
import team_questio.questio.user.persentation.dto.SignUpRequest;
import team_questio.questio.user.persentation.dto.UserInfoResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApiController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequest request) {
        var command = request.toCommand();
        userService.registerUser(command);
    }

    @GetMapping("/remaining")
    public ResponseEntity<RemainingResponse> getRemaining(Authentication authentication) {
        var id = Long.valueOf(authentication.getPrincipal().toString());
        var quota = userService.getQuota(id);

        var response = RemainingResponse.of(quota);
        return ResponseEntity.ok()
            .body(response);
    }

    @GetMapping("/username")
    public ResponseEntity<UserInfoResponse> getUsername(Authentication authentication) {
        var id = Long.valueOf(authentication.getPrincipal().toString());
        var username = userService.getUsername(id);

        var response = UserInfoResponse.of(username);
        return ResponseEntity.ok()
                .body(response);
    }
}
