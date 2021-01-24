package com.kwgdev.brewery.msscinventoryfailover.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by kw on 1/23/2021 @ 11:21 AM
 */
@EnableDiscoveryClient
@Profile("local-discovery")
@Configuration
public class LocalDiscovery {
}