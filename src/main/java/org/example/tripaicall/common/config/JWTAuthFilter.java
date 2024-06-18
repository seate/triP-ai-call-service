package org.example.tripaicall.common.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.tripaicall.common.dto.ValidateAccessTokenRequestDTO;
import org.springframework.web.client.RestClient;

public class JWTAuthFilter implements Filter {

    private final RestClient restClient;

    public JWTAuthFilter(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            boolean isValid = validateToken(jwtToken);

            if (isValid) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or Invalid Authorization Header");
        }
    }

    private boolean validateToken(String token) {
        Boolean isValid = restClient.post()
                .body(new ValidateAccessTokenRequestDTO(token))
                .retrieve()
                .body(Boolean.class);
        return isValid != null && isValid;
    }

    @Override
    public void destroy() {
    }
}