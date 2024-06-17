package org.example.tripaicall.common.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "https://asdf-6e088.web.app/"); // 모든 도메인 허용
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // 모든 메서드 허용
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600"); // 프리플라이트 요청의 캐시 시간 설정
        response.setHeader("Access-Control-Allow-Credentials", "true");


        System.out.println("CORS 필터 작동");
        System.out.println("Access-Control-Allow-Origin" + response.getHeader("Access-Control-Allow-Origin"));
        System.out.println("Access-Control-Allow-Methods" + response.getHeader("Access-Control-Allow-Methods"));
        System.out.println("Access-Control-Allow-Headers" + response.getHeader("Access-Control-Allow-Headers"));
        System.out.println("Access-Control-Allow-Credentials" + response.getHeader("Access-Control-Allow-Origin"));
        System.out.println("Access-Control-Max-Age" + response.getHeader("Access-Control-Max-Age"));

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}