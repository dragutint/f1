package com.sporty.f1.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventFinishedEvent extends ApplicationEvent {

    private final String eventId;
    private final String winnerDriverId;

    public EventFinishedEvent(Object source, String eventId, String winnerDriverId) {
        super(source);
        this.eventId = eventId;
        this.winnerDriverId = winnerDriverId;
    }

}
