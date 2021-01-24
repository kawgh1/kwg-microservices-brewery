package com.kwgdev.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * created by kw on 1/12/2021 @ 8:15 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderResult {
    private UUID orderId;
    private Boolean isValid;
}
