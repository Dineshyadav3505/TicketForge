package com.monk.InventoryService.controller;

import com.monk.InventoryService.response.EventInventoryResponse;
import com.monk.InventoryService.response.VenueInventoryResponse;
import com.monk.InventoryService.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("api/v1")
public class InventoryController {

    final private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents() {
        try {
            return inventoryService.getAllEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponse inventoryByVenueId(@PathVariable("venueId") Long venueId) {
        try {
            return inventoryService.getVenueInformation(venueId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/inventory/event/{eventId}")
    public @ResponseBody EventInventoryResponse inventoryForEvent(@PathVariable("eventId") Long eventId) {
        return inventoryService.getEventInventory(eventId);
    }

    @PutMapping("/inventory/event/{eventId}/capacity/{capacity}")
    public ResponseEntity<Void> updateEventCapacity(@PathVariable("eventId") Long eventId,
                                                    @PathVariable("capacity") Long ticketsBooked) {
        inventoryService.updateEventCapacity(eventId, ticketsBooked);
        return ResponseEntity.ok().build();
    }

}