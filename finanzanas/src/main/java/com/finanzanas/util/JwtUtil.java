package com.finanzanas.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Clave secreta para firmar el token
    // Se crea una clave de 256 bits para firmar el token
    private Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave segura automáticamente

    // Extraer el nombre de usuario del token JWT (subject) para verificar si el token es válido
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer la fecha de expiración del token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer todas las reclamaciones del token JWT para obtener información adicional
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Verificar si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generar un token JWT para un usuario
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Crear un token JWT basado en las reclamaciones y el nombre de usuario
    /*
    * Jwts.builder() crea un nuevo token JWT
    * setClaims(claims) establece las reclamaciones del token
    * setSubject(subject) establece el nombre de usuario como el subject del token
    * setIssuedAt(new Date(System.currentTimeMillis())) establece la fecha de emisión del token
    * setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) establece la fecha de expiración del token (10 horas)
    * signWith(SignatureAlgorithm.HS256, SECRET_KEY) firma el token con el algoritmo HS256 y la clave secreta
    * compact() compacta el token en una cadena de texto
    * */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validar el token JWT para un usuario
    /*
    * validateToken(token, username) verifica si el token es válido para un usuario específico
    * extractUsername(token) extrae el nombre de usuario del token
    * isTokenExpired(token) verifica si el token ha expirado
    * */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
