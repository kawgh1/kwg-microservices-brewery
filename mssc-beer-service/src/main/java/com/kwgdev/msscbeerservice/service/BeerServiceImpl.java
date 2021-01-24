package com.kwgdev.msscbeerservice.service;

import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.msscbeerservice.repositories.BeerRepository;
import com.kwgdev.msscbeerservice.web.controller.NotFoundException;
import com.kwgdev.msscbeerservice.web.mappers.BeerMapper;
import com.kwgdev.brewery.model.BeerDto;
import com.kwgdev.brewery.model.BeerPagedList;
import com.kwgdev.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * created by kw on 12/11/2020 @ 2:32 PM
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    // cacheNames corresponds to name defined in resources/ehcache.xml
    // conditional caching - only cache this value when showInventoryOnHand is false
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {



        // this method goes out and builds a page object with the information from the Beer Repository
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        // these are the various queries defined in BeerRepository, se we're running these queries and converting their
        // return values in Page objects
        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            // else if we don't have anything from the above, we run findAll to find any other beers
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand){
            // http://localhost:8080/api/v1/beer/?showInventoryOnHand=true
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    // get a list of beer objects from the repository and convert to BeerDtos using beerMapper
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        } else {
            // http://localhost:8080/api/v1/beer/
            System.out.println("listBeers caching was initiated here");
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    // cacheNames corresponds to name defined in resources/ehcache.xml
    // conditional caching - only cache this value when showInventoryOnHand is false
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            // http://localhost:8080/api/v1/beer/a712d914-61ea-4623-8bd0-32c0f6545bfd/?showInventoryOnHand=true example
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        } else {
            // http://localhost:8080/api/v1/beer/a712d914-61ea-4623-8bd0-32c0f6545bfd example
            System.out.println("getBeer caching was initiated here");
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc) {

        // http://localhost:8080/api/v1/beerUpc/654654657656
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
