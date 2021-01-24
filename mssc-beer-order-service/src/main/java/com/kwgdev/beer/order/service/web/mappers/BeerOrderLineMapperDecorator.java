package com.kwgdev.beer.order.service.web.mappers;

import com.kwgdev.beer.order.service.domain.BeerOrderLine;
import com.kwgdev.beer.order.service.services.beer.BeerService;
import com.kwgdev.brewery.model.BeerOrderLineDto;
import com.kwgdev.brewery.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

/**
 * created by kw on 12/28/2020 @ 10:55 PM
 */
public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        // convert to BeerOrderLine to BeerOrderLineDto
        BeerOrderLineDto orderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
        // call beer service
        Optional<BeerDto> beerDtoOptional = beerService.getBeerByUpc(line.getUpc());

        // if we get a beer back, enhance it with the following info from beer service
        beerDtoOptional.ifPresent(beerDto -> {
            orderLineDto.setBeerName(beerDto.getBeerName());
            orderLineDto.setBeerStyle(beerDto.getBeerStyle());
            orderLineDto.setPrice(beerDto.getPrice());
            orderLineDto.setBeerId(beerDto.getId());
        });

        // return the order line

        return orderLineDto;
    }
}