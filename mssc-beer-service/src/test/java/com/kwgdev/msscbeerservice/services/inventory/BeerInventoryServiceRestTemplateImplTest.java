package com.kwgdev.msscbeerservice.services.inventory;

import com.kwgdev.msscbeerservice.service.inventory.BeerInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * created by kw on 12/27/2020 @ 10:03 PM
 */
@Disabled // utility for manual testing
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getOnhandInventory() {

        //todo evolve to use UPC
//        Integer qoh = beerInventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID);

//        System.out.println(qoh);

    }
}