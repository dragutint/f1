package com.sporty.f1.provider.api;

import com.sporty.f1.dto.EventDto;
import com.sporty.f1.dto.F1EventProviderEnum;

import java.util.List;

public interface F1EventProvider {

    List<EventDto> getEvents(String sessionType, Integer year, String countryCode);
    EventDto getEventById(String eventId);

    F1EventProviderEnum getProviderType();
}
