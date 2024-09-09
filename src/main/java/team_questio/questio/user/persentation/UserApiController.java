package team_questio.questio.user.persentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import team_questio.questio.user.persentation.dto.RemainingResponse;
import team_questio.questio.user.persentation.dto.SignUpRequest;
import team_questio.questio.user.persentation.dto.UserInfoResponse;

@Tag(name = "사용자 담당 API", description = "사용자 정보를 담당합니다")
public interface UserApiController {

    @Operation(summary = "회원가입을 진행합니다.", description = "회원가입 시 제출되는 username은 인증이 완료된 이메일 주소입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "A006", description = "회원가입 시 제출된 이메일이 인증되지 않았습니다."),
            @ApiResponse(responseCode = "A009", description = "이미 가입된 이메일입니다")
    })
    @PostMapping
    void signUp(@RequestBody(description = "회원가입 시 저장될 사용자 정보입니다.", required = true) SignUpRequest request);

    @Operation(summary = "잔여 횟수를 조회합니다.", description = "잔여 업로드 횟수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "잔여 횟수 조회 성공")
    })
    ResponseEntity<RemainingResponse> getRemaining(Authentication authentication);

    @Operation(summary = "유저 이메일를 조회합니다", description = "유저 이메일을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 이메일 조회 성공"),
            @ApiResponse(responseCode = "A010", description = "로그인 되지 않은 유저입니다")
    })
    ResponseEntity<UserInfoResponse> getUsername(Authentication authentication);
}
