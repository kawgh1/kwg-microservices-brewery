package com.kwgdev.brewery.msscinventoryfailover.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * created by kw on 1/23/2021 @ 11:23 AM
 */
@Configuration
public class RouterConfig {

    // here we're saying, when /inventory-failover is GET Requested, call InventoryHandler to handle the request

    @Bean
    public RouterFunction inventoryRoute(InventoryHandler inventoryHandler){
        return route(GET("/inventory-failover").and(accept(MediaType.APPLICATION_JSON)),
                inventoryHandler::listInventory);
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
