package com.kwgdev.brewery.msscinventoryfailover.web;

import com.kwgdev.brewery.msscinventoryfailover.model.BeerInventoryDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * created by kw on 1/23/2021 @ 11:22 AM
 */

// Reactive Web Flux

/**
* The idea with this Inventory Failover Service is if our main Beer Inventory Service fails or is unavailable,
* this Failover service will kick in and at least provide inventory values (999) for new Beer Inventory Requests.
* It may not be able to fulfill any Order Requests, but can at least prevent some downstream errors
* of failed inventory requests.
*/

@Component
public class InventoryHandler {

    // this method handles the request of how things come through
    // we're just building a Beer Inventory POJO, setting random UUID for id, All 0's for beerId, QuantityOnHand default of 999
    // and returning this stub
    public Mono<ServerResponse> listInventory(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(Arrays.asList(
                        BeerInventoryDto.builder()
                                .id(UUID.randomUUID())
                                .beerId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                                .quantityOnHand(999)
                                .createdDate(OffsetDateTime.now())
                                .lastModifiedDate(OffsetDateTime.now())
                                .build())), List.class);
    }

//     example of returned JSON
//        [
//            {
//                "id": "ecceecfb-a756-469a-915b-8f39b5fc2631",
//                    "createdDate": "2021-01-23T11:50:50.603881-06:00",
//                    "lastModifiedDate": "2021-01-23T11:50:50.603881-06:00",
//                    "beerId": "00000000-0000-0000-0000-000000000000",
//                    "quantityOnHand": 999
//            }
//        ]

}
