package com.kwgdev.beer.order.service.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by kw on 1/18/2021 @ 10:24 AM
 */
@Profile("local-discovery")
@EnableDiscoveryClient
@Configuration
public class LocalDiscovery {
}
