package com.company.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PublicKey;

@Configuration
public class JwtParserProvider {

    private final PublicKey publicKey;

    public JwtParserProvider(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Bean
    public JwtParser jwtParser() {

        return Jwts
                .parserBuilder()
                .setSigningKey(publicKey)
                .build();
    }
}