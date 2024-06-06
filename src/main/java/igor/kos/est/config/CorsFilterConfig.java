package igor.kos.est.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsFilterConfig {
    static final String CORS_BASE_PATTERN = "/**";

    @Value("${service.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${service.cors.allowed-methods}")
    private List<String> allowedMethods;

    @Value("${service.cors.allowed-headers}")
    private List<String> allowedHeaders;


    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(allowedOrigins);
        config.setAllowedMethods(allowedMethods);
        config.setAllowedHeaders(allowedHeaders);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_BASE_PATTERN, config);

        final var bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
