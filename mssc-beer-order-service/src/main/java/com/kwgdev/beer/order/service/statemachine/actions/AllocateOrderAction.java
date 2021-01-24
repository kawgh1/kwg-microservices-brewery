package com.kwgdev.beer.order.service.statemachine.actions;

import com.kwgdev.beer.order.service.config.JmsConfig;
import com.kwgdev.beer.order.service.domain.BeerOrder;
import com.kwgdev.beer.order.service.domain.BeerOrderEventEnum;
import com.kwgdev.beer.order.service.domain.BeerOrderStatusEnum;
import com.kwgdev.beer.order.service.repositories.BeerOrderRepository;
import com.kwgdev.beer.order.service.services.BeerOrderManagerImpl;
import com.kwgdev.beer.order.service.web.mappers.BeerOrderMapper;
import com.kwgdev.brewery.model.events.AllocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * created by kw on 1/10/2021 @ 10:30 AM
 */

// because we have @Component annotation, Spring will create a bean with bean name allocateOrderAction with a lower case "a"
// so this matches to statemachine/BeerOrderStateMachineConfig.allocateOrderAction

    // so by having this same as the class name of the bean or make them match up, we don't have to do any type qualifier
    // this is a hidden Spring trick
@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    // On this action when we get a request from the State Machine we are going to go look up the beer order,
    // get that out of the repository and then do a JMS template convertAndSend and send that allocation request
    // off to the JMS queue.

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
                    AllocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
            log.debug("Sent Allocation Request for order id: " + beerOrderId);
        }, () -> log.error("Beer Order Not Found!"));
    }
}
