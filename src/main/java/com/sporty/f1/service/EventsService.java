package com.sporty.f1.service;

import com.sporty.f1.events.EventFinishedEvent;
import com.sporty.f1.exceptions.BadRequestException;
import com.sporty.f1.exceptions.NotFoundException;
import com.sporty.f1.dto.EventDto;
import com.sporty.f1.dto.FinishEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsService {

    private final F1ProviderService f1ProviderService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<EventDto> searchEvents(String eventType, Integer year, String countryCode) {

        log.info("Searching events with eventType: {}, year: {}, countryCode: {}", eventType, year, countryCode);

        final List<EventDto> events = f1ProviderService.getEvents(eventType, year, countryCode);

        log.info("Found {} events for eventType: {}, year: {}, countryCode: {}", events.size(), eventType, year, countryCode);

        return events;
    }

    public void finishEvent(String eventId, FinishEventRequest request) {

        log.info("Finish event request received for eventId: {}, with request: {}", eventId, request);

        final EventDto event = f1ProviderService.getEventById(eventId);

        if (event == null) {
            throw new NotFoundException("Event with ID " + eventId + " not found");
        }

        if (request == null || StringUtils.isBlank(request.getWinnerDriverId())) {
            throw new BadRequestException("Winner driver ID must be provided to finish the event");
        }

        // Validate that the provided winner driver ID is part of the event's drivers
        if (event.getDrivers() == null || event.getDrivers().stream().noneMatch(driver -> driver.getId().equals(request.getWinnerDriverId()))) {
            throw new BadRequestException("Winner driver ID " + request.getWinnerDriverId() + " is not part of the event's drivers");
        }

        applicationEventPublisher.publishEvent(new EventFinishedEvent(this, eventId, request.getWinnerDriverId()));

        log.info("Finished event with id: {}, with request: {}, publishing EventFinishedEvent", eventId, request);
    }
}
