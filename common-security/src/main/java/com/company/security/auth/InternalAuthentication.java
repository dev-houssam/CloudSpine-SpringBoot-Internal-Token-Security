package com.company.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class InternalAuthentication extends AbstractAuthenticationToken {

    private final InternalPrincipal principal;
    private final String token;

    public InternalAuthentication(
            String userId,
            String tenantId,
            String token,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = new InternalPrincipal(userId, tenantId);
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getToken() {
        return token;
    }
}