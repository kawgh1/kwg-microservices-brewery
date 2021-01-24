package com.kwgdev.msscbeerservice.service.inventory;

import com.kwgdev.msscbeerservice.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

/**
 * created by kw on 12/27/2020 @ 9:07 PM
 */

// The way the FeignClient works is a lot like  Spring Data JPA
// we provide the interface and decorate it with some annotations and then at runtime Spring will provide an implementation for us

@FeignClient(name = "inventory-service", fallback = InventoryServiceFeignClientFailover.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = BeerInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(@PathVariable UUID beerId);
}
