package io.microsamples.security.ui.config;

import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RoutingConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, TokenRelayGatewayFilterFactory tokenRelay) {
        return builder.routes()
                .route("chachkie", r -> r.path("/chachkie/**")
                        .filters(f -> f.filter(tokenRelay.apply()))
                        .uri("http://localhost:8180/chachkie"))
                .build();
    }
}
