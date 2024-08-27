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
import org.springframework.web.util.UriComponentsBuilder;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.security.application.JWTTokenService;
import team_questio.questio.security.application.dto.PrincipleDetails;

@Slf4j
public class AuthenticationResultHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final JWTTokenService jwtTokenService;
    private static final String HOME_URL = "https://questio.co.kr/oauth";

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

        String redirectUrl = UriComponentsBuilder.fromUriString(HOME_URL)
                .queryParam("response_type", "code")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();
        
        response.sendRedirect(redirectUrl);
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
