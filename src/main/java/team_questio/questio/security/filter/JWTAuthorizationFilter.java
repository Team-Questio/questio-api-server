package team_questio.questio.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.security.util.JWTTokenProvider;

public class JWTAuthorizationFilter extends AbstractPreAuthenticatedProcessingFilter {
    private static final String DEFAULT_ERROR_CODE = "errorCode";
    private final JWTTokenProvider jwtTokenProvider;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) request;
        var token = httpServletRequest.getHeader("Authorization");

        if (isInvalidToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        var authentication = getUsernamePasswordAuthenticationToken(token.substring(7));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token) {
        var claims = jwtTokenProvider.getAccessClaims(token);
        var roles = (List<String>) claims.get("roles");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(() -> role));

        return new UsernamePasswordAuthenticationToken(claims.get("id"), null, authorities);
    }

    private boolean isInvalidToken(String token) {
        return isInvalidTokenForm(token) || jwtTokenProvider.isInvalidAccessToken(token.substring(7));
    }

    private boolean isInvalidTokenForm(String token) {
        return Objects.isNull(token) || !token.startsWith("Bearer ");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        var errorCode = AuthError.AUTHORIZATION_FAILED;
        response.setStatus(errorCode.httpStatus().value());

        var problemDetail = ProblemDetail.forStatusAndDetail(errorCode.httpStatus(), errorCode.message());
        problemDetail.setProperty(DEFAULT_ERROR_CODE, errorCode.code());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(problemDetail));
    }
}
