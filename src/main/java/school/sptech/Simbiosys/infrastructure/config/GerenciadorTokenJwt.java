package school.sptech.Simbiosys.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.Simbiosys.core.dto.UsuarioDetalhesDto;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    public String getUsernameFromToken(String token) {
        return getClaimForToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String generateToken(final Authentication authentication) {

        // Autoridades
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Pegando o objeto do usuário logado (precisa ser um UserDetails customizado!)
        Object principal = authentication.getPrincipal();

        // Verifique se é seu tipo personalizado
        if (!(principal instanceof UsuarioDetalhesDto)) {
            throw new IllegalArgumentException("Principal não é do tipo esperado.");
        }

        UsuarioDetalhesDto usuario = (UsuarioDetalhesDto) principal;

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .claim("id", usuario.getId()    )
                .claim("nome", usuario.getNome())
                .claim("sobrenome", usuario.getSobrenome())
                .claim("email", usuario.getEmail())
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1_000))
                .signWith(parseSecret())
                .compact();
    }

    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private SecretKey parseSecret() {
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }
}
