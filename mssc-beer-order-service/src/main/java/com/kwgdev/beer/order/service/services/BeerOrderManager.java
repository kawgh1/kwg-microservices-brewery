package com.kwgdev.beer.order.service.services;

import com.kwgdev.beer.order.service.domain.BeerOrder;
import com.kwgdev.brewery.model.BeerOrderDto;

import java.util.UUID;

/**
 * created by kw on 1/10/2021 @ 9:14 AM
 */
public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);

    void beerOrderAllocationPassed(BeerOrderDto beerOrder);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrder);

    void beerOrderAllocationFailed(BeerOrderDto beerOrder);

    void beerOrderPickedUp(UUID id);

    void cancelOrder(UUID id);
}
