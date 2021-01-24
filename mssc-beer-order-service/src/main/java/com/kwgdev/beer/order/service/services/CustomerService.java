package com.kwgdev.beer.order.service.services;

import com.kwgdev.brewery.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;

/**
 * created by kw on 1/10/2021 @ 10:25 PM
 */
public interface CustomerService {

    CustomerPagedList listCustomers(Pageable pageable);

}
