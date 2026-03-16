package com.company.security.tenant;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.function.Function;

public class ReactiveTenantContext {

    public static final String TENANT_KEY = "tenant";

    public static Mono<String> getTenant() {
        return Mono.deferContextual(ctx -> {
            String tenant = ctx.getOrDefault(TENANT_KEY, null);
            return Mono.justOrEmpty(tenant);
        });
    }

    public static Function<Context, Context> withTenant(String tenant) {
        return context -> context.put(TENANT_KEY, tenant);
    }
}