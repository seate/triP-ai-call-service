package org.example.tripaicall.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("CorsFilter");
        return registrationBean;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public FilterRegistrationBean<JWTAuthFilter> jwtAuthFilter() {
        FilterRegistrationBean<JWTAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTAuthFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("JWTAuthFilter");
        return registrationBean;
    }
}