package com.sporty.f1.provider.impl.openf1;

import com.sporty.f1.dto.DriverDto;
import com.sporty.f1.dto.EventDto;
import com.sporty.f1.dto.F1EventProviderEnum;
import com.sporty.f1.exceptions.BadRequestException;
import com.sporty.f1.provider.api.F1EventProvider;
import com.sporty.f1.provider.impl.openf1.dto.Driver;
import com.sporty.f1.provider.impl.openf1.dto.Session;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenF1EventProvider implements F1EventProvider {

    private final OpenF1RemoteClient remoteClient;

    @Override
    public List<EventDto> getEvents(String eventType, Integer year, String countryCode) {

        final List<Session> sessions = remoteClient.getSessions(eventType, year, countryCode, null);

        final List<Driver> drivers = remoteClient.getDrivers(null, null);

        return sessions.stream()
                .map(session -> {
                    final EventDto event = mapEvent(session);
                    event.setDrivers(drivers.stream()
                            .filter(driver -> driver.getSessionKey() != null && String.valueOf(driver.getSessionKey()).equals(event.getId()))
                            .map(this::mapDriver)
                            .toList());
                    return event;
                })
                .toList();
    }

    @Override
    public EventDto getEventById(String eventId) {

        final List<Session> sessions = remoteClient.getSessions(null, null, null, eventId);

        if (sessions.size() != 1) {
            throw new BadRequestException("Event with ID " + eventId + " not found or multiple events found");
        }

        final EventDto event = mapEvent(sessions.getFirst());
        event.setDrivers(getDriversByEventId(event.getId()));
        return event;
    }

    @Override
    public F1EventProviderEnum getProviderType() {
        return F1EventProviderEnum.OPEN_F1;
    }

    // private

    private List<DriverDto> getDriversByEventId(String eventId) {

        final List<Driver> drivers = remoteClient.getDrivers(eventId, null);

        return drivers.stream()
                .map(this::mapDriver)
                .toList();
    }

    private DriverDto mapDriver(Driver driver) {

        return DriverDto.builder()
                .id(driver.getDriverNumber() != null ? driver.getDriverNumber().toString() : null)
                .name(driver.getFullName())
                .odd((double) RandomUtils.secure().randomInt(2, 5))
                .build();
    }

    private EventDto mapEvent(Session session) {

        return EventDto.builder()
                .id(String.valueOf(session.getSessionKey()))
                .name(session.getSessionName())
                .type(session.getSessionType())
                .startDate(session.getDateStart())
                .endDate(session.getDateEnd())
                .location(session.getLocation())
                .year(session.getYear())
                .countryCode(session.getCountryCode())
                .build();
    }
}
