package com.kwgdev.beer.order.service.statemachine.actions;

import com.kwgdev.beer.order.service.domain.BeerOrderEventEnum;
import com.kwgdev.beer.order.service.domain.BeerOrderStatusEnum;
import com.kwgdev.beer.order.service.services.BeerOrderManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * created by kw on 1/11/2021 @ 6:52 PM
 */
@Slf4j
@Component
public class ValidationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        log.error("Compensating Transaction.... Validation Failed: " + beerOrderId);
    }
}