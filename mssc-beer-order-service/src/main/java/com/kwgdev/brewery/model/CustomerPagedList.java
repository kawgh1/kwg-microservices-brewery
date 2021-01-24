package com.kwgdev.brewery.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * created by kw on 1/10/2021 @ 10:26 PM
 */
public class CustomerPagedList extends PageImpl<CustomerDto> {
    public CustomerPagedList(List<CustomerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomerPagedList(List<CustomerDto> content) {
        super(content);
    }
}
