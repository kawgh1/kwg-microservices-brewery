package com.kwgdev.msscbeerservice.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.awt.*;

/**
 * created by kw on 1/3/2021 @ 12:44 PM
 */
@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory";
    public static final String VALIDATE_ORDER_QUEUE = "validate-order";
    public static final String VALIDATE_ORDER_RESPONSE_QUEUE = "validate-order-response";

    @Bean // Serialize message content to JSON using TextMessage
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        // if we don't set this Jackson/Spring Boot managed object mapper, it defaults to the object mapper from the BeerDto
        // which uses java.time.OffsetDateTime - which is not what Jackson/JMS uses
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
