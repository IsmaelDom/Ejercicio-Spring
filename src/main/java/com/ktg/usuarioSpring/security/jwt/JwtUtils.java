package com.ktg.usuarioSpring.security.jwt;

import com.ktg.usuarioSpring.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;

@Log
@Component
public class JwtUtils {
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.ttlMillis}")
    private int jwtExpirationMs;

    //Genera un JWT con nombre de usuario, fecha de vencimiento
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        log.log(Level.INFO,"jwtUtil: " + userPrincipal);
        return Jwts.builder()
                .setSubject((userPrincipal.getCorreo()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //Obtiene el nombre de usuario o correo de JWT.
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    //Valida un JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.log(Level.SEVERE,"Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.log(Level.SEVERE,"Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.log(Level.SEVERE,"JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.log(Level.SEVERE,"JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE,"JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
