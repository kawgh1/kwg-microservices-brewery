package com.kwgdev.msscbrewerygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by kw on 1/13/2021 @ 12:03 PM
 */
@Profile("!local-discovery")
@Configuration
public class LocalHostRouteConfig {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder) {

        // For our beer service we can run routes like http://localhost:8080/api/v1/beer to see available beers
        // this code now allows the same requests to be made to port 9090
        // the server port 9090 is set in resources/application.properties file
        // http://localhost:9090/api/v1/beer etc.

        return builder.routes()
                .route(r -> r.path("/api/v1/beer/*", "/api/v1/beer*", "/api/v1/beerUpc/*")
                        .uri("http://localhost:8080")
                        .id("beer-service"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081")
                        .id("order-service"))
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .uri("http://localhost:8082")
                        .id("inventory-service"))
                .build();
    }
}
