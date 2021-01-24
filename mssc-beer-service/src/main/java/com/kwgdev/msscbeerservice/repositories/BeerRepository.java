package com.kwgdev.msscbeerservice.repositories;

import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * created by kw on 12/25/2020 @ 11:12 PM
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    // create custom queries against the underlying database to find beer

    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    Beer findByUpc(String upc);
}
