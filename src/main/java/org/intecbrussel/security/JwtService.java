package org.intecbrussel.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.intecbrussel.model.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "Password"; // tekent de token

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail()) // subject =  wie is de gebruiker, email unique
                .claim("role",user.getRole().name()) // extra info in token + rol?
                .setIssuedAt(new Date()) // Token geldig 1 dag
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Token wordt cryptografidch ondertekent, niemand kan vervalsen
                .compact();

    }

    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

//         haal email uit token
    }

    public boolean IsTokenValid(String token, User user){
        return  extractUsername(token).equals(user.getEmail());
//         Controleer token hoort bij deze user
    }
}
