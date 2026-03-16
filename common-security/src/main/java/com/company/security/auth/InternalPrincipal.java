package com.company.security.auth;

public record InternalPrincipal(
        String userId,
        String tenantId
) {}