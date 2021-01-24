package com.kwgdev.msscbeerservice.bootstrap;

import com.kwgdev.brewery.model.BeerStyleEnum;

import com.kwgdev.msscbeerservice.domain.Beer;
import com.kwgdev.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * created by kw on 12/25/2020 @ 11:14 PM
 */
@RequiredArgsConstructor
@Component // CommandLineRunner means this class runs every time Spring Context starts
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {

//        beerRepository.deleteAll();

        // Confirm number of initial beers in console
        System.out.println("Initial Beers: " + beerRepository.count());

        // if BeerRepository is empty on startup, then save some beers there for data
        // If this app is connected to a persistent datasource with existing data, this code will be skipped
        if(beerRepository.count() == 0 ) {
            loadBeerObjects();
        }

        // Confirm number of beers loaded in console
        System.out.println("Loaded Beers: " + beerRepository.count());
    }

    private void loadBeerObjects() {

        Beer b1 = Beer.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_1_UPC)
                .build();

        Beer b2 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.PALE_ALE.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_2_UPC)
                .build();

        Beer b3 = Beer.builder()
                .beerName("Pinball Porter")
                .beerStyle(BeerStyleEnum.PALE_ALE.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_3_UPC)
                .build();

        beerRepository.save(b1);
        beerRepository.save(b2);
        beerRepository.save(b3);

        // Confirm number of beers loaded in console
        System.out.println("Loaded Beers: " + beerRepository.count());
    }


}
