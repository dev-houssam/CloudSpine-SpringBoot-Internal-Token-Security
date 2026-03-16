public class InternalTokenFilter extends OncePerRequestFilter {

    //private final JwtParser parser;
    private final AuthMetrics metrics;
    public static final String INTERNAL_HEADER = "X-Internal-Auth";
    private final InternalTokenValidator tokenValidator;

    public InternalTokenFilter( AuthMetrics metrics, InternalTokenValidator tokenValidator) {
        this.metrics = metrics;
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/actuator")
                || path.startsWith("/swagger");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String token = request.getHeader(INTERNAL_HEADER);

        if (token == null) {
            metrics.tokenMissing();
            response.sendError(401, "Missing token");
            return;
        }

        try {

            Claims claims = tokenValidator.parse(token);

            Integer version = claims.get("version", Integer.class);

            if (version == null || version < 1) {
                response.sendError(401, "Unsupported token version");
                return;
            }

            String userId = claims.get("userId", String.class);
            String tenantId = claims.get("tenantId", String.class);
            List<String> roles = ((List<?>) claims.get("roles"))
                .stream()
                .map(Object::toString)
                .toList();
            List<GrantedAuthority> authorities =
                    roles.stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .toList();

            InternalAuthentication auth =
                    new InternalAuthentication(userId, tenantId, token, authorities);

            try {
                SecurityContextHolder.getContext().setAuthentication(auth);
                TenantContext.set(tenantId);
                chain.doFilter(request, response);
            } finally {
                TenantContext.clear();
                SecurityContextHolder.clearContext();
            }

            metrics.success();

        } catch (ExpiredJwtException e) {

            metrics.tokenExpired();
            response.sendError(401, "Token expired");

        } catch (JwtException e) {

            metrics.invalidSignature();
            response.sendError(401, "Invalid token");

        }
    }
}