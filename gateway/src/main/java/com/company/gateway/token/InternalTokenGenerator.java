package com.company.gateway.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class InternalTokenGenerator {

    private final PrivateKey privateKey;

    public InternalTokenGenerator(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String generate(String userId,
                           String tenantId,
                           List<String> roles) {

        return Jwts.builder()
                .claim("version", 1)
                .claim("userId", userId)
                .claim("tenantId", tenantId)
                .claim("roles", roles)
                .setExpiration(Date.from(Instant.now().plusSeconds(300)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

}