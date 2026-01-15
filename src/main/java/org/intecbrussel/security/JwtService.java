package org.intecbrussel.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.intecbrussel.model.User;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my-super-secret-jwt-key-my-super-secret-jwt-key"; // tekent de token


    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail()) // subject =  wie is de gebruiker, email unique
                .claim("role",user.getRole().name()) // extra info in token + rol?
                .setIssuedAt(new Date()) // Token geldig 1 dag
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Token wordt cryptografidch ondertekent, niemand kan vervalsen
                .compact();

    }


    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
