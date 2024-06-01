package com.demoProject.security;

import com.demoProject.security.models.SessionStatus;
import com.demoProject.security.respository.SessionRepository;
import com.demoProject.security.respository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(getSignInKey()).build().parseSignedClaims(token);

            // Check if token ID is found in the session repository
            boolean isTokenValid = sessionRepository.findByToken(token)
                    .map(session -> {
                        if (session.getSessionStatus() != SessionStatus.ACTIVE) {
                            return false;
                        }
                        if (session.getExpiringAt() != null && session.getExpiringAt().toInstant().isBefore(Instant.now())) {
                            session.setSessionStatus(SessionStatus.ENDED);
                            sessionRepository.save(session); // Save the updated session status
                            return false;
                        }
                        return true;
                    })
                    .orElse(false);

            return isTokenValid;

        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(role -> new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_" + role;
                    }
                })
                .collect(Collectors.toList());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}


