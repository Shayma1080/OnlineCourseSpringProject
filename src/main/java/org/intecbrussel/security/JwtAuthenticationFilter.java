package org.intecbrussel.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // filter draait bij elke request
    // OncePerRequestFilter= exact 1 keer per request

    private final JwtService jwtService; // token lezen
    private final UserRepository userRepository; // user

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); // wordt auto aangeroepen door Spring

        if(authHeader == null || !authHeader.startsWith("Bearer ")){ // geen token gewoon verder, security beslist later
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7); // Bearer verwijderen
        String email = jwtService.extractEmail(token); // email uit token halen

        User user = userRepository.findByEmail(email).orElseThrow(); // user ophalen uit db

        UsernamePasswordAuthenticationToken authToken = // user is ingelogd met deze rol
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("Role_" + user.getRole()))
                );

        SecurityContextHolder.getContext().setAuthentication(authToken); // Spring weet nu wie je bent
        filterChain.doFilter(request, response); // Controller mag uitgevoerd worden

    }
}
