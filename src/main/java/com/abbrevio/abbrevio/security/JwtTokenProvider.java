package com.abbrevio.abbrevio.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        }
        catch(MalformedJwtException malformedJwtException) {
            throw new Exception("Invalid JWT Token");
        }
        catch(ExpiredJwtException expiredJwtException) {
            throw new Exception("JWT Token has expired");
        }
        catch(UnsupportedJwtException unsupportedJwtException) {
            throw new Exception("Unsupported JWT token");
        }
        catch(IllegalArgumentException illegalArgumentException) {
            throw new Exception("JWT claims string is null or empty");
        }
        catch(SignatureException signatureException) {
            throw new SignatureException("Signature does not match");
        }
    }
}
