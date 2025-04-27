package io.shortcut.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.shortcut.utils.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final long VALID_IN_MS = 3600000; // 1 hour
    private final KeyUtil keyUtil;

    //todo: on top of signing the token could also be encrypted to increase security
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + VALID_IN_MS))
                .signWith(keyUtil.getPrivateKey(), Jwts.SIG.EdDSA)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(keyUtil.getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
