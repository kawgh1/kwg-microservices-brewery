package com.kwgdev.brewery.model.events;

import com.kwgdev.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by kw on 1/10/2021 @ 10:24 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderResult {
    private BeerOrderDto beerOrderDto;
    private Boolean allocationError = false;
    private Boolean pendingInventory = false;
}