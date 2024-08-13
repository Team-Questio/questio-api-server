package team_questio.questio.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.security.application.JWTTokenService;
import team_questio.questio.security.application.PrincipleDetails;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JWTTokenService jwtTokenService;
    private static final String DEFAULT_ERROR_CODE = "errorCode";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTTokenService jwtTokenService) {
        super(authenticationManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        PrincipleDetails userDetails = (PrincipleDetails) authResult.getPrincipal();

        var accessToken = jwtTokenService.generateAccessToken(userDetails);
        var refreshToken = jwtTokenService.generateRefreshToken(userDetails);

        setResponse(response, accessToken, refreshToken);
    }

    private void setResponse(HttpServletResponse response, String accessToken, String refreshToken)
            throws IOException {
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("accessToken", accessToken);
        tokenResponse.put("refreshToken", refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.error("Authentication failed: {}", failed.getMessage());
        var errorCode = AuthError.USER_NOT_FOUND;
        response.setStatus(errorCode.httpStatus().value());

        var problemDetail = ProblemDetail.forStatusAndDetail(errorCode.httpStatus(), errorCode.message());
        problemDetail.setProperty(DEFAULT_ERROR_CODE, errorCode.code());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(problemDetail));
    }
}
