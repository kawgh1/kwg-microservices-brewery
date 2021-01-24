package com.kwgdev.msscbeerservice.service.inventory;

import java.util.UUID;

/**
 * created by kw on 12/27/2020 @ 8:58 PM
 */
// since we're coding to an interface here, we can have multiple implementations of BeerInventoryService
public interface BeerInventoryService {

    Integer getOnhandInventory(UUID beerId);
}