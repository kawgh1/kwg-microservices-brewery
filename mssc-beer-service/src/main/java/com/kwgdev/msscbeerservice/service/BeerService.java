package com.kwgdev.msscbeerservice.service;

import com.kwgdev.brewery.model.BeerDto;
import com.kwgdev.brewery.model.BeerPagedList;
import com.kwgdev.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * created by kw on 12/11/2020 @ 2:31 PM
 */
public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerDto getByUpc(String upc);
}