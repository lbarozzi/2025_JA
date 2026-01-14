package it.eforhum.demo.config;

import it.eforhum.demo.filter.ApiKeyFilter;
import it.eforhum.demo.filter.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    
    private final ApiKeyFilter apiKeyFilter;
    private final RequestLoggingFilter requestLoggingFilter;

    public SecurityConfig(ApiKeyFilter apiKeyFilter, RequestLoggingFilter requestLoggingFilter) {
        this.apiKeyFilter = apiKeyFilter;
        this.requestLoggingFilter = requestLoggingFilter;
    }

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(withDefaults());
        return http.build();
    }   
}
