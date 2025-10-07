package pos.java.bora_comer.infra.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.user.User;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService { // gera e valida tokens

    // chave secreta (use uma bem grande e segura, pode gerar com base64)
    private static final String SECRET_KEY = "u8v1Q2h3J4k5L6m7N8o9P0q1R2s3T4u5V6w7X8y9Z0a1B2c3D4e5F6g7H8i9J0k1";

    // tempo de expiração
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 6; // 6 horas

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // gerar token com username
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getUserTypeNameEnum().name()) // CLIENTE ou DONO_RESTAURANTE
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // extrair username do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // validar token (usuário + expiração)
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
