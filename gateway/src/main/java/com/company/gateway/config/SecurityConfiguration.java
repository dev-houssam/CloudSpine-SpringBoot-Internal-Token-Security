@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final InternalTokenFilter tokenFilter;
    private final AuthMetrics metrics;

    public SecurityConfiguration(
        InternalTokenFilter tokenFilter,
        AuthMetrics metrics
    ) {
        this.tokenFilter = tokenFilter;
        this.metrics = metrics;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
        )
        .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

    @Bean
    public JwtParser jwtParser(JwtParserProvider provider) {
        return provider.getParser();
    }
}