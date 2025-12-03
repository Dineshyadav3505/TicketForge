package com.monk.InventoryService.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueInventoryResponse {
    private Long venueId;
    private String venueName;
    private Long totalCapacity;
}
