package com.kwgdev.beer.inventory.service.services;

import com.kwgdev.beer.inventory.service.config.JmsConfig;
import com.kwgdev.brewery.model.events.DeallocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * created by kw on 1/12/2021 @ 7:56 AM
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListener {

    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request){
        allocationService.deallocateOrder(request.getBeerOrderDto());
    }
}
