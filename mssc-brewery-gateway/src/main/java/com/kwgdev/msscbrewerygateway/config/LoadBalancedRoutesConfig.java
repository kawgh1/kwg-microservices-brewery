package com.kwgdev.msscbrewerygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by kw on 1/18/2021 @ 10:50 AM
 */
@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfig {

    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder) {

        // For our beer service we can run routes like http://localhost:8080/api/v1/beer to see available beers
        // this code now allows the same requests to be made to port 9090
        // the server port 9090 is set in resources/application.properties file
        // http://localhost:9090/api/v1/beer etc.

        return builder.routes()
                .route(r -> r.path("/api/v1/beer/*", "/api/v1/beer*", "/api/v1/beerUpc/*")
//                        .uri("http://localhost:8080")
                        // "lb" is for load balanced - and this is how the Gateway will look up services on Eureka
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(r -> r.path("/api/v1/customers/**")
//                        .uri("http://localhost:8081")
                        .uri("lb://order-service")
                        .id("order-service"))
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        // inventory failover CIRCUIT BREAKER filter using Resilience4J
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")
                                ))
//                        .uri("http://localhost:8082")
                        .uri("lb://inventory-service")
                        .id("inventory-service"))
                .route(r -> r.path("/inventory-failover/**")
//                        .uri("http://localhost:8083")
                        .uri("lb://inventory-failover")
                        .id("inventory-failover-service"))
                .build();
    }
}
