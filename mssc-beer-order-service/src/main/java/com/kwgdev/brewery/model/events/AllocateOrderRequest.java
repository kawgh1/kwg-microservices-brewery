package com.kwgdev.brewery.model.events;

import com.kwgdev.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by kw on 1/10/2021 @ 10:34 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderRequest {

    private BeerOrderDto beerOrderDto;
}
