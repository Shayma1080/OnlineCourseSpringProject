package org.intecbrussel.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguratie {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // elk request gaat hier eerst door
        http.csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**")
                        .permitAll() // login en register zijn open
                        .requestMatchers("/admin/**").hasRole("ADMIN") // alleen admin
                        .requestMatchers("/student/**").hasRole("STUDENT") // alleen student
                        .anyRequest().authenticated()) // alle andere ingelogde vereist
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT = geen sessies, elke request moet token meebrengen
                .addFilterBefore(jwtAuthenticationFilter, usernamePasswordAuthenticationFilter.class); // JWT filter komt voor Spring zijn login-filter
        return http.build(); // Spring kijkt eerst naar het token

    }
}
