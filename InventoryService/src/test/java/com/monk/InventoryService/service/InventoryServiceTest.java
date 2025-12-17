package com.monk.InventoryService.service;

import com.monk.InventoryService.entity.EventEntity;
import com.monk.InventoryService.entity.VenueEntity;
import com.monk.InventoryService.repository.EventRepository;
import com.monk.InventoryService.response.VenueInventoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    @Mock
    private EventRepository eventRepository;


    @Test
    void testGetVenueInformation() throws Exception {
        // Arrange - Create test data
        VenueEntity venueEntity = new VenueEntity();
        venueEntity.setId(1L);
        venueEntity.setName("Guru Gra");
        venueEntity.setAddress("kjsdbrglkj ksdj gjh a ghk");
        venueEntity.setTotalCapacity(2000L);

        EventEntity eventEntity = EventEntity.builder()
                .id(1L)
                .name("Gurugram")
                .totalCapacity(100L)
                .leftCapacity(110L)
                .venue(venueEntity)
                .ticketPrice(BigDecimal.valueOf(500.0))
                .build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(eventEntity));  // Fixed: Optional + correct ID

        // Act
        VenueInventoryResponse response = inventoryService.getVenueInformation(1L);  // Fixed: use primitive long

        // Assert
        assertNotNull(response);
        assertEquals("Guru Gra", response.getVenueName());  // Adjust based on your response fields
    }


}
