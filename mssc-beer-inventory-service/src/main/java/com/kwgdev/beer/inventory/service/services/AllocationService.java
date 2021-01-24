package com.kwgdev.beer.inventory.service.services;

import com.kwgdev.brewery.model.BeerOrderDto;

/**
 * created by kw on 1/10/2021 @ 10:52 AM
 */
public interface AllocationService {

    Boolean allocateOrder(BeerOrderDto beerOrderDto);

    void deallocateOrder(BeerOrderDto beerOrderDto);

}
