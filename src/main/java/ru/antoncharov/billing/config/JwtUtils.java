package ru.antoncharov.billing.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtils {

    private SecretKey key;

    @PostConstruct
    public void init(){
        key = Jwts.SIG.HS256.key().build();
    }

    public String trimTokenFromBearer(String token) {
        assert (token != null);
        return (token.startsWith("Bearer ")) ? token.substring(7) : token;
    }

    private Claims getAllClaimsFromToken(String token) {
        token = trimTokenFromBearer(token);
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getBody();
    }

    public String getNameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String generateToken(String login) {
        return Jwts.builder()
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }
}
