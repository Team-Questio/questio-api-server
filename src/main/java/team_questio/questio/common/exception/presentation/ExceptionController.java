package team_questio.questio.common.exception.presentation;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.common.exception.code.GPTError;
import team_questio.questio.common.exception.code.PortfolioError;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/auth")
    public ResponseEntity<List<ExceptionCodeResponse>> getGPTError() {
        var errors = Arrays.stream(AuthError.values())
                .map(ExceptionCodeResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(errors);
    }

    @GetMapping("/portfolio")
    public ResponseEntity<List<ExceptionCodeResponse>> getPortfolioError() {
        var errors = Arrays.stream(PortfolioError.values())
                .map(ExceptionCodeResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(errors);
    }

    @GetMapping("/gpt")
    public ResponseEntity<List<ExceptionCodeResponse>> getGptErrors() {
        var errors = Arrays.stream(GPTError.values())
                .map(ExceptionCodeResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(errors);
    }

}
