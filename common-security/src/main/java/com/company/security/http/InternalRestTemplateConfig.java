@Configuration
public class InternalRestTemplateConfig {

    @Bean
    public RestTemplate internalRestTemplate(
            InternalTokenInterceptor interceptor
    ) {

        RestTemplate template = new RestTemplate();

        template.getInterceptors().add(interceptor);

        return template;
    }
}