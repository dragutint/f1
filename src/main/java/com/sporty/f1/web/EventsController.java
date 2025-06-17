package com.sporty.f1.web;

import com.sporty.f1.dto.EventDto;
import com.sporty.f1.service.EventsService;
import com.sporty.f1.dto.FinishEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/formula/v1/events")
@RequiredArgsConstructor
@RestController
public class EventsController {

    private final EventsService eventsService;

    @GetMapping
    public ResponseEntity<List<EventDto>> searchEvents(@RequestParam(name = "eventType", required = false) String eventType,
                                                       @RequestParam(name = "year", required = false) Integer year,
                                                       @RequestParam(name = "countryCode", required = false) String countryCode) {

        return ResponseEntity.ok(eventsService.searchEvents(eventType, year, countryCode));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Void> finishEvent(@PathVariable String eventId, @RequestBody FinishEventRequest request) {
        eventsService.finishEvent(eventId, request);
        return ResponseEntity.ok().build();
    }

}
