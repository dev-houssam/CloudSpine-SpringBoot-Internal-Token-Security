package com.company.security.tenant;

import org.springframework.core.NamedThreadLocal;

public class TenantContext {

    private static final ThreadLocal<String> tenant =
            new NamedThreadLocal<>("tenant-context");

    public static void set(String tenantId) {
        tenant.set(tenantId);
    }

    public static String get() {
        return tenant.get();
    }

    public static void clear() {
        tenant.remove();
    }
}