package com.kwgdev.msscbeerservice.web.mappers;

import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.brewery.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * created by kw on 12/26/2020 @ 2:41 AM
 */
@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}