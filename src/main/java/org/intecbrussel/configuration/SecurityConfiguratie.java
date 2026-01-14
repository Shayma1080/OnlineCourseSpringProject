package org.intecbrussel.configuration;

import lombok.RequiredArgsConstructor;
import org.intecbrussel.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguratie {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // elk request gaat hier eerst door
        http
                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // 🛡 Admin endpoints
                        .requestMatchers(HttpMethod.GET, "/api/admin/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/admin/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/admin/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/enrollments").hasRole("ADMIN")

                        // 👨‍🏫 Instructor endpoints
                        .requestMatchers(HttpMethod.GET, "/api/instructor/enrollments").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/courses/{instructorId}").hasAnyRole("INSTRUCTOR","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/courses/{courseId}").hasAnyRole("INSTRUCTOR","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/{courseId}").hasAnyRole("INSTRUCTOR","ADMIN")

                        // 📚 Courses (open voor GET)
                        .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/courses/{id}").permitAll()

                        // 📝 Enrollment endpoints
                        .requestMatchers(HttpMethod.POST, "/api/courses/{studentId}/enroll").hasAnyRole("STUDENT","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/enrollments/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/courses/{studentId}/enrollments").hasRole("STUDENT") // nieuw: GET inschrijvingen
                        .requestMatchers(HttpMethod.DELETE, "/api/enrollments/{enrollmentId}").hasAnyRole("STUDENT","ADMIN")

                        // 🔒 Alles andere vereist login
                        .anyRequest().authenticated()) // alle andere ingelogde vereist
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT = geen sessies, elke request moet token meebrengen
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filter komt voor Spring zijn login-filter
        return http.build(); // Spring kijkt eerst naar het token

    }
}
