package com.kwgdev.beer.order.service.services.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

import com.kwgdev.brewery.model.events.ValidateOrderResult;
import com.kwgdev.beer.order.service.services.BeerOrderManager;
import com.kwgdev.beer.order.service.config.JmsConfig;


import java.util.UUID;
/**
 * created by kw on 1/10/2021 @ 11:25 AM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result){
        final UUID beerOrderId = result.getOrderId();

        log.debug("Validation Result for Order Id: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, result.getIsValid());
    }
}
