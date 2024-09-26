package com.example.usermanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLE = "role";

    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        if (authentication.isAuthenticated())
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return createToken(userDetails);
        } else {
            throw new RuntimeException("Autenticación fallida"); // O lanza una excepción más específica
        }
    }

    public String createToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Date getExpirationDateFromToken(String token) {

        Date expiration;

        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }

        return expiration;
    }

    public boolean validToken(String token) {
        return !expiredToken(token);
    }

    private boolean expiredToken(String token) {
        Date expirationDate = this.getExpirationDateFromToken(token);
        if (expirationDate == null) {
            return false;
        }
        return expirationDate.before(new Date());
    }

    private Claims getClaimsFromToken(String token) throws AuthenticationException {

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    public List<String> getRolesFromToken(String token) {
        return (List<String>) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("roles");
    }

}
