package team_questio.questio.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.security.application.JWTTokenService;
import team_questio.questio.security.application.dto.PrincipleDetails;
import team_questio.questio.security.application.dto.TokenDetails;

@Slf4j
public class AuthenticationResultHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final JWTTokenService jwtTokenService;

    public AuthenticationResultHandler(JWTTokenService jwtTokenProvider) {
        this.jwtTokenService = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipleDetails userDetails = (PrincipleDetails) authentication.getPrincipal();

        var accessToken = jwtTokenService.generateAccessToken(userDetails);
        var refreshToken = jwtTokenService.generateRefreshToken(userDetails);

        setSuccessResponse(response, accessToken, refreshToken);
    }

    private void setSuccessResponse(HttpServletResponse response, String accessToken, String refreshToken)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        TokenDetails tokenResponse = new TokenDetails(accessToken, refreshToken);

        response.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error("Authentication failed: {}", exception.getMessage());
        var errorCode = AuthError.USER_NOT_FOUND;
        response.setStatus(errorCode.httpStatus().value());

        var problemDetail = ProblemDetail.forStatusAndDetail(errorCode.httpStatus(), errorCode.message());
        problemDetail.setProperty("ERROR", errorCode.code());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(problemDetail));
    }

}
