package com.sporty.f1.provider.impl.anotherf1;

import com.sporty.f1.dto.EventDto;
import com.sporty.f1.provider.api.F1EventProvider;
import com.sporty.f1.dto.F1EventProviderEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnotherF1EventProvider implements F1EventProvider {


    @Override
    public List<EventDto> getEvents(String sessionType, Integer year, String countryCode) {
        // another custom implementation to fetch events
        return null;
    }

    @Override
    public EventDto getEventById(String eventId) {
        // another custom implementation to fetch event by ID
        return null;
    }

    @Override
    public F1EventProviderEnum getProviderType() {
        return F1EventProviderEnum.ANOTHER_F1;
    }
}
