package com.company.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.stereotype.Service;

@Service
public class InternalTokenValidator {

    private final JwtParser parser;

    public InternalTokenValidator(JwtParser parser) {
        this.parser = parser;
    }

    public Claims parse(String token) {
        return parser
                .parseClaimsJws(token)
                .getBody();
    }

}