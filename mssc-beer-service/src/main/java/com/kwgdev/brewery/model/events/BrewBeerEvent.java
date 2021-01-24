package com.kwgdev.brewery.model.events;

import com.kwgdev.brewery.model.BeerDto;
import lombok.*;

/**
 * created by kw on 1/3/2021 @ 1:02 PM
 */
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
