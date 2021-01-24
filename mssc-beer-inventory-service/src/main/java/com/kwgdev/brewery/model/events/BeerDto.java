package com.kwgdev.brewery.model.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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
 * created by kw on 12/11/2020 @ 2:02 PM
 */
// lombok annotations - constructors, getters, setters, etc.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable  {

    static final long serialVersionUID = -5815566940065181210L;


    @Null
    private UUID id;

    @Null
    private Integer version;

    @Null
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;

    @Null
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime lastModifiedDate;

    @NotBlank
    private String beerName;

    @NotNull
    private String beerStyle;

    @NotNull
    private String upc;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Positive
    @NotNull
    private BigDecimal price;

    private Integer quantityOnHand;

}