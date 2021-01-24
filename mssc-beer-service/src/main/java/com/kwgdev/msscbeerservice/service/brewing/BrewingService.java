package com.kwgdev.msscbeerservice.service.brewing;

import com.kwgdev.msscbeerservice.config.JmsConfig;
import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.brewery.model.events.BrewBeerEvent;
import com.kwgdev.msscbeerservice.web.mappers.BeerMapper;
import com.kwgdev.msscbeerservice.repositories.BeerRepository;
import com.kwgdev.msscbeerservice.service.inventory.BeerInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

/**
 * created by kw on 1/3/2021 @ 1:06 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll(); // return all the beer object records

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("Checking Inventory for: " + beer.getBeerName() + " / " + beer.getId());
            log.debug("Min Onhand is: " + beer.getMinOnHand());
            log.debug("Inventory is: "  + invQOH);

            // if beer minimum >- Inventory Quantity on Hand - send message to brew more beer!!
            if(beer.getMinOnHand() >= invQOH){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }

        });
    }
}
