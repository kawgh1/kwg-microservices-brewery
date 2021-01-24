package com.kwgdev.beer.order.service.statemachine.actions;

import com.kwgdev.beer.order.service.config.JmsConfig;
import com.kwgdev.beer.order.service.domain.BeerOrder;
import com.kwgdev.beer.order.service.domain.BeerOrderEventEnum;
import com.kwgdev.beer.order.service.domain.BeerOrderStatusEnum;
import com.kwgdev.beer.order.service.repositories.BeerOrderRepository;
import com.kwgdev.beer.order.service.services.BeerOrderManagerImpl;
import com.kwgdev.beer.order.service.web.mappers.BeerOrderMapper;
import com.kwgdev.brewery.model.events.ValidateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * created by kw on 1/10/2021 @ 9:40 AM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                    .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
                    .build());
        }, () -> log.error("Order Not Found. Id: " + beerOrderId));

        log.debug("Sent Validation request to queue for order id " + beerOrderId);
    }
}
