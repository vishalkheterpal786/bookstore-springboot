package com.katachallenge.bookstore.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
//            return true;
//        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
//                 IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
