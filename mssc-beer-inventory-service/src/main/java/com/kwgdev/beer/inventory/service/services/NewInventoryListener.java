package com.kwgdev.beer.inventory.service.services;

import com.kwgdev.beer.inventory.service.config.JmsConfig;
import com.kwgdev.beer.inventory.service.domain.BeerInventory;
import com.kwgdev.beer.inventory.service.repositories.BeerInventoryRepository;
import com.kwgdev.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * created by kw on 1/3/2021 @ 3:14 PM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){

        log.debug("Got Inventory: " + event.toString());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(event.getBeerDto().getId())
                .upc(event.getBeerDto().getUpc())
                .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                .build());
    }

}
