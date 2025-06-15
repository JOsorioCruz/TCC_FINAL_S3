package edu.unicartagena.tcc.carrito.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extraerCorreo(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extraerUsuario(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, String username) {
        final String correoExtraido = extraerCorreo(token);
        return correoExtraido.equals(username) && !estaExpirado(token);
    }

    public boolean esTokenValido(String token) {
        try {
            return !estaExpirado(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean estaExpirado(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}