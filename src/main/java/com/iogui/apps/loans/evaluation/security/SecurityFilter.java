package com.iogui.apps.loans.evaluation.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilter {
    private final String[] PUBLIC_ACCESS = {
            "/api/v1/interest-rates/**"
    };
    private final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("filterChain...");
        http.cors(t -> t.configurationSource(corsConfigurationSource()));
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(PUBLIC_ACCESS).permitAll();

            // Customer endpoints - different roles for different operations
            auth.requestMatchers(HttpMethod.GET, "/api/v1/customers/**")
                    .hasAnyAuthority("CUSTOMER_VIEWER");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/customers/**")
                    .hasAnyAuthority("CUSTOMER_SERVICE");
            // Loan endpoints - role-based access
            auth.requestMatchers(HttpMethod.GET, "/api/v1/customers/*/loan-applications")
                    .hasAnyAuthority("CUSTOMER_VIEWER");
            auth.requestMatchers(HttpMethod.POST, "/api/v1/loan-applications/**")
                    .hasAnyAuthority("LOAN_OFFICER", "LOAN_MANAGER");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/loan-applications/*/risk-assessment")
                    .hasAnyAuthority("RISK_ANALYST");
            auth.requestMatchers(HttpMethod.PUT, "/api/v1/loan-applications/*/decision")
                    .hasAnyAuthority("LOAN_APPROVER", "LOAN_MANAGER");

            // Any other request must be fully authenticated
            auth.anyRequest().fullyAuthenticated();
        }).oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())); // OAuth2

        return http.build();
    }

    // CORS

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedHeaders(
                Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.setAllowedMethods(Arrays.asList(HttpMethod.OPTIONS.name(), HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
