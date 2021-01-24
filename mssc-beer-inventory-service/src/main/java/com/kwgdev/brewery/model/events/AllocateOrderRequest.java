package com.kwgdev.brewery.model.events;

import com.kwgdev.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by kw on 1/12/2021 @ 8:02 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderRequest {
    private BeerOrderDto beerOrderDto;
}