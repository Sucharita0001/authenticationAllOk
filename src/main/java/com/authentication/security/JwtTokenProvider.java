package com.authentication.security;


import com.authentication.exception.CustomAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class JwtTokenProvider {
    private final static String jwtSecret = "SECRET";
    private final static long jwtExpirationTime = 60 * 1000;

    //generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + Long.valueOf(jwtExpirationTime));
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return jwtToken;
    }

    //get username from JWT token
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret) //sending the key
                .build()
                .parseClaimsJws(token)
                .getBody()//geting body
                .getSubject(); //geting username from the body. As we are sending username as subject so using getSubject
    }

    //validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new CustomAuthenticationException(BAD_REQUEST, "Inavlid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new CustomAuthenticationException(BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new CustomAuthenticationException(BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new CustomAuthenticationException(BAD_REQUEST, "JWT claims string is empty");
        }
    }
}