package com.monk.InventoryService.service;

import com.monk.InventoryService.entity.EventEntity;
import com.monk.InventoryService.entity.VenueEntity;
import com.monk.InventoryService.repository.EventRepository;
import com.monk.InventoryService.repository.VenueRepository;
import com.monk.InventoryService.response.EventInventoryResponse;
import com.monk.InventoryService.response.VenueInventoryResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jdk.jfr.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public InventoryService(EventRepository eventRepository, VenueRepository venueRepository){
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @RateLimiter(name = "inventory-events-rate-limit", fallbackMethod = "getAllEventsFallback")
    public List<EventInventoryResponse> getAllEvents() throws Exception {
        final List<EventEntity> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build()).collect(Collectors.toList());
    }

    @RateLimiter(name = "inventory-events-rate-limit", fallbackMethod = "getAllEventsFallback")
    public VenueInventoryResponse getVenueInformation(final Long venueId) throws Exception  {
        final VenueEntity venue = venueRepository.findById(venueId).orElse(null);

        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();
    }

    @RateLimiter(name = "inventory-events-rate-limit", fallbackMethod = "getAllEventsFallback")
    public EventInventoryResponse getEventInventory(final Long eventId) {
        final EventEntity event = eventRepository.findById(eventId).orElse(null);

        return EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .eventId(event.getId())
                .build();
    }

    @RateLimiter(name = "inventory-events-rate-limit", fallbackMethod = "getAllEventsFallback")
    public void updateEventCapacity(final Long eventId, final Long ticketsBooked) {
        final EventEntity event = eventRepository.findById(eventId).orElse(null);
        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for event id: {} with tickets booked: {}", eventId, ticketsBooked);
    }


    public String getAllEventsFallback(Throwable t) {
        log.error("Rate limit exceeded for getAllEvents. Returning fallback.");
        return "Rate limit exceeded for getAllEvents. Returning fallback.";
    }
}
