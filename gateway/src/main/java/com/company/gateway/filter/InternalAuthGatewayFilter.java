package com.company.gateway.filter;

import com.company.gateway.token.InternalTokenGenerator;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class InternalAuthGatewayFilter implements GlobalFilter {

    private final InternalTokenGenerator tokenGenerator;

    public InternalAuthGatewayFilter(InternalTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /*gateway ne valide pas le token externe
        String externalJwt =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");
        */

        // TODO validation réelle
        String userId = "123";
        String tenantId = "tenantA";

        //Probleme param here
        String token = tokenGenerator.generate(
            userId,
            tenantId,
            List.of("USER")
        );
        return chain.filter(
                exchange.mutate()
                        .request(
                                exchange.getRequest()
                                        .mutate()
                                        .header(
                                            InternalTokenFilter.INTERNAL_HEADER, 
                                            token)
                                        .build()
                        )
                        .build()
        );
    }
}