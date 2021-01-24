package com.kwgdev.brewery.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/** Just for the sake of persistence and caching of domain objects, Serializable is not needed.
 * Spring boot and Jackson are sufficient. But it is best practice to make them Serializable.

 * According to JPA spec:

 * An entity instance is to be passed by value as a detached object (e.g., through a remote interface),
 * the entity class must implement the Serializable interface.


 * One example when making your domain object serializable helps.
 * When domain objects are directly exposed to the presentation layer,
 * we could just pass the detached entities as value holders to the front-end.
 * In that case, we need to implement Serializable.
 */

/**
 * created by kw on 12/11/2020 @ 2:09 PM
 */
public class BeerPagedList extends PageImpl<BeerDto> implements Serializable {

    static final long serialVersionUID = 1114715135625836949L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerPagedList(@JsonProperty("content") List<BeerDto> content,
                         @JsonProperty("number") int number,
                         @JsonProperty("size") int size,
                         @JsonProperty("totalElements") Long totalElements,
                         @JsonProperty("pageable") JsonNode pageable,
                         @JsonProperty("last") boolean last,
                         @JsonProperty("totalPages") int totalPages,
                         @JsonProperty("sort") JsonNode sort,
                         @JsonProperty("first") boolean first,
                         @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public BeerPagedList(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPagedList(List<BeerDto> content) {
        super(content);
    }
}
