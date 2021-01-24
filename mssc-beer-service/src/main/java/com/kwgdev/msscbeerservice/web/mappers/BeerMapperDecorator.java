package com.kwgdev.msscbeerservice.web.mappers;

import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.msscbeerservice.service.inventory.BeerInventoryService;
import com.kwgdev.brewery.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by kw on 12/27/2020 @ 8:55 PM
 */
// abstract class
public abstract class BeerMapperDecorator implements BeerMapper {
    // contains an instance of beerInventoryService and a Beer Mapper
    // uses them together to
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    // MapStruct was not happy with a constructor dependency injection, it wants a no-args constructor
    // used method dependency injection instead

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {

        // take in a beer object, return BeerDto
        return mapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        // take in a beer object, convert to BeerDto
        BeerDto dto = mapper.beerToBeerDto(beer);
        // set inventory with service call to beerInventoryService of that BeerDto
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }
}
