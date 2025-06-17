package com.sporty.f1.service;

import com.sporty.f1.dto.EventDto;
import com.sporty.f1.provider.api.F1EventProvider;
import com.sporty.f1.dto.F1EventProviderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class F1ProviderService {

    private final Map<F1EventProviderEnum, F1EventProvider> eventProviderMap;

    public F1ProviderService(Set<F1EventProvider> eventProviders) {

        if (eventProviders == null) {
            throw new IllegalArgumentException("Event providers cannot be null");
        }

        this.eventProviderMap = eventProviders.stream()
                .collect(Collectors.toMap(F1EventProvider::getProviderType, provider -> provider));

        log.info("Loaded {} event providers", eventProviderMap.size());
    }

    public List<EventDto> getEvents(String eventType, Integer year, String countryCode) {

        return resolveEventProvider().getEvents(eventType, year, countryCode);
    }

    public EventDto getEventById(String eventId) {

        if (eventId == null || eventId.isBlank()) {
            throw new IllegalArgumentException("Event ID cannot be null or blank");
        }

        return resolveEventProvider().getEventById(eventId);
    }

    // private

    private F1EventProvider resolveEventProvider() {
        // We want to use the OpenF1 provider for this example, if we have some other provider, the business logic
        // can be extended to choose the provider based on some criteria.
        return eventProviderMap.get(F1EventProviderEnum.OPEN_F1);
    }
}
