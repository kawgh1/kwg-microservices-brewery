package com.kwgdev.beer.order.service.web.mappers;

import com.kwgdev.beer.order.service.domain.Customer;
import com.kwgdev.brewery.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * created by kw on 1/10/2021 @ 10:28 PM
 */
@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(Customer dto);
}
