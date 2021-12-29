package com.barauna.DEVinHouse.utils;

import com.barauna.DEVinHouse.dto.response.JwtTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenUtils {
    private String secret;
    private Long expiration;

    public JWTTokenUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public JwtTokenDTO generateToken(String login) {
        Date tokenExpiration = new Date(System.currentTimeMillis() + expiration);
        String token = Jwts.builder()
                .setSubject(login)
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return new JwtTokenDTO("Bearer", token, tokenExpiration.getTime());
    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String email = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return email != null && expiration != null && now.before(expiration);
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return parseClaimsJws.getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
