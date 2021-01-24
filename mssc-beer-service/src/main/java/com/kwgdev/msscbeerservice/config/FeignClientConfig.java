package com.kwgdev.msscbeerservice.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by kw on 12/27/2020 @ 9:23 PM
 */
@Configuration
public class FeignClientConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor( @Value("${com.kwgdev.brewery.inventory-user}") String inventoryUser,
                                                                    @Value("${com.kwgdev.brewery.inventory-password}")String inventoryPassword){
        return new BasicAuthRequestInterceptor(inventoryUser, inventoryPassword);
    }
}
