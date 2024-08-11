package team_questio.questio.security.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import team_questio.questio.security.presentation.dto.EmailAuthRequest;

@Tag(name = "인증 담당 API", description = "회원가입 시 필요한 인증을 담당합니다.")
public interface AuthenticationApiController {

    @Operation(summary = "이메일 인증을 요청합니다.", description = "요청 이메일로 인증 코드를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 전송이 성공하였습니다."),
            @ApiResponse(responseCode = "A002", description = "이메일 전송에 실패했습니다.")
    })
    @PostMapping
    void authenticateEmail(@RequestBody(description = "인증을 원하는 Email 주소입니다.", required = true)
                           EmailAuthRequest emailAuthRequest);

    @Operation(summary = "이메일 인증을 확인합니다.", description = "인증 코드를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증이 성공하였습니다."),
            @ApiResponse(responseCode = "A003", description = "인증 코드가 만료되었거나 존재하지 않습니다."),
            @ApiResponse(responseCode = "A004", description = "인증 코드가 일치하지 않습니다. 인증코드가 폐기됩니다.")
    })
    @PostMapping
    void verifyEmail(@RequestBody(description = "인증을 원하는 Email 주소입니다.", required = true)
                     EmailAuthRequest emailAuthRequest,
                     @Parameter(description = "인증 코드입니다.", required = true)
                     String code);
}
