package com.kwgdev.msscbeerservice.service.brewing;

import com.kwgdev.msscbeerservice.config.JmsConfig;
import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.brewery.model.events.BrewBeerEvent;
import com.kwgdev.brewery.model.events.NewInventoryEvent;
import com.kwgdev.brewery.model.BeerDto;
import com.kwgdev.msscbeerservice.repositories.BeerRepository;
import org.springframework.jms.core.JmsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by kw on 1/3/2021 @ 1:53 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional //hibernate
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        // VERY SIMPLE BREWING LOGIC
        // set quantity on hand to the amount supposed to be brewed
        // in real world - this would be much more complex/involved
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
