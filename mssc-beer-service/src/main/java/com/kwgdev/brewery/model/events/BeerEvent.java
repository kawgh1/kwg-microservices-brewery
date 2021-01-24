package com.kwgdev.brewery.model.events;

import com.kwgdev.brewery.model.BeerDto;
import lombok.*;

import java.io.Serializable;

/**
 * created by kw on 1/3/2021 @ 1:01 PM
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor // Jackson wants a no args constructor to function properly
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -791324981724981L;

    private BeerDto beerDto;
}
