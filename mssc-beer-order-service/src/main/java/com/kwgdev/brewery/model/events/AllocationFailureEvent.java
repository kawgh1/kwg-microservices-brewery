package com.kwgdev.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


/**
 * created by kw on 1/10/2021 @ 10:36 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationFailureEvent {
    private UUID orderId;
}
