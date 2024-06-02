package com.demoProject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    @Value("${jwt.token.issuer}")
    private String jwtIssuer;

    @Value("${jwt.public.key.file-path}")
    private String publicKeyFilePath;

    @Autowired
    private ClientRegistrationUtil clientRegistrationUtil;

    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            this.publicKey = clientRegistrationUtil.loadPublicKey();
        } catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .requireIssuer(jwtIssuer)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
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

