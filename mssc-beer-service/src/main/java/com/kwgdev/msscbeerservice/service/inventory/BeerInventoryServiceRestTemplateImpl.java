package com.kwgdev.msscbeerservice.service.inventory;

import com.kwgdev.msscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * created by kw on 12/27/2020 @ 9:21 PM
 */

@Profile("!local-discovery")
@Slf4j
@ConfigurationProperties(prefix = "com.kwgdev.brewery", ignoreUnknownFields = true) //true
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private final RestTemplate restTemplate;

    // bind to sfg.brewery.beer-inventory-service-host=http://localhost:8082 - in application.properties
    private String beerInventoryServiceHost;

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder,
                                                @Value("${com.kwgdev.brewery.inventory-user}") String inventoryUser,
                                                @Value("${com.kwgdev.brewery.inventory-password}")String inventoryPassword) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(inventoryUser, inventoryPassword)
                .build();
    }


//    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplate = restTemplateBuilder.build();
//    }

    @Override
    public Integer getOnhandInventory(UUID beerId) {

        log.debug("Calling Inventory Service");
        System.out.println(beerInventoryServiceHost + INVENTORY_PATH);

        // this part binds the beer object into the URL {beerId} of "/api/v1/beer/{beerId}/inventory"
        ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate
                .exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BeerInventoryDto>>(){}, (Object) beerId);

        //sum from inventory list
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                // stream all the beer
                .stream()
                // convert to integer of quantity on hand
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                // add that all together
                .sum();

       log.debug("Inventory is -> " + onHand);
        return onHand;
    }
}
