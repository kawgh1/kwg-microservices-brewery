package com.kwgdev.msscbrewerygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by kw on 1/11/2021 @ 10:10 PM
 */
@Profile("google")
@Configuration
public class GoogleConfig {

    // provide a bean that is a route locator
    // Java configuration for configuring routes within Spring Cloud Gateway
    /**
     * This is just an example of routing requests to Google from localhost. For demonstration only
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator googleRouteConfig(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/googlesearch2")
                        // regex at segment
                        .filters(f -> f.rewritePath("/googlesearch2(?<segment>/?.*)", "/${segment}"))
                        .uri("https://google.com")
                        .id("google"))
                .build();
    }
}
