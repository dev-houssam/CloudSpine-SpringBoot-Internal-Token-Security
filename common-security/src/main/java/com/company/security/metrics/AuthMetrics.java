@Component
public class AuthMetrics {

    private final Counter success;
    private final Counter missing;
    private final Counter expired;
    private final Counter failure;//
    private final Counter invalidSignature;

    public AuthMetrics(MeterRegistry registry) {
        this.success = registry.counter("auth.success");
        this.failure = registry.counter("auth.failure");
        this.missing = registry.counter("auth.token.missing");
        this.expired = registry.counter("auth.token.expired");
        this.invalidSignature = registry.counter("auth.token.invalid");
    }

    public void success() { success.increment(); }
    public void failure() { failure.increment(); }
    public void tokenMissing() { missing.increment(); }
    public void tokenExpired() { expired.increment(); }
    public void invalidSignature() { invalidSignature.increment(); }
}