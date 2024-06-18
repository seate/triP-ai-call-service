package org.example.tripaicall.common.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestClient;

@Configuration
public class FilterConfig {

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
        registrationBean.setFilter(new JWTAuthFilter(this.restClient));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("JWTAuthFilter");
        return registrationBean;
    }
}
