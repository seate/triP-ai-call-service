/*
package org.example.tripaicall.common.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    private String AllowedOriginPatterns = "*";
    private String AllowedHeaders = "*";
    private String AllowedMethods = "*";

    @Bean //bean으로 등록하면 withDefault로도 자동으로 적용됨
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList(AllowedOriginPatterns));
        config.setAllowedHeaders(Collections.singletonList(AllowedHeaders));
        config.setAllowedMethods(Collections.singletonList(AllowedMethods));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
*/
