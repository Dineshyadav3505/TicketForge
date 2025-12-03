package com.monk.InventoryService.response;

import com.monk.InventoryService.entity.VenueEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EventInventoryResponse {
    private Long eventId;
    private String event;
    private Long capacity;
    private VenueEntity venue;
    private BigDecimal ticketPrice;
}
