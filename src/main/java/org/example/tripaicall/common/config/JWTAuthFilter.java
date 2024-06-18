package org.example.tripaicall.common.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

public class JWTAuthFilter implements Filter {
    @Value("${jwt.validate.url}")
    private String validateUrl;

    private RestClient restClient;

    @PostConstruct
    private void initRestClient() {
        this.restClient = RestClient.builder()
                .baseUrl(validateUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
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
        String validationUrl = "https://trip-ani.kro.kr/token/validate";
        Boolean isValid = restClient.post()
                .uri(validationUrl)
                .body(token)
                .retrieve()
                .body(Boolean.class);
        return isValid != null && isValid;
    }

    @Override
    public void destroy() {
    }
}
