package com.careers.CareerHub.service;

import com.careers.CareerHub.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "careerhub_secret_key_very_long";

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
