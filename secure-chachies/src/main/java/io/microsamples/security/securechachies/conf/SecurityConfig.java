package io.microsamples.security.securechachies.conf;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    @SneakyThrows
    SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .cors(Customizer.withDefaults())
                .authorizeRequests((auth) -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    WebMvcConfigurer webMvc() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/chachkie/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowedHeaders("X-CSRF-TOKEN")
                        .exposedHeaders("X-CSRF-TOKEN")
                        .allowCredentials(true)
                        .maxAge(0);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("alex")
                        .password("passw0rd")
                        .authorities("app")
                        .build()
        );
    }
}
