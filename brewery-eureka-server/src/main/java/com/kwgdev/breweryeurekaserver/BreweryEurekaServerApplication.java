package com.kwgdev.breweryeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BreweryEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreweryEurekaServerApplication.class, args);
    }

}
