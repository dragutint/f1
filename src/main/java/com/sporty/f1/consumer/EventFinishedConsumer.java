package com.sporty.f1.consumer;

import com.sporty.f1.domain.BetStatusEnum;
import com.sporty.f1.dto.BetDto;
import com.sporty.f1.events.EventFinishedEvent;
import com.sporty.f1.service.BetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventFinishedConsumer implements ApplicationListener<EventFinishedEvent> {

    private final BetsService betsService;

    @Override
    public void onApplicationEvent(EventFinishedEvent event) {

        log.info("Received EventFinishedEvent for processing: {}", event);

        final List<BetDto> bets = betsService.findByEventIdAndStatus(event.getEventId(), BetStatusEnum.PLACED);

        log.debug("Found {} bets for finished event with id: {}, winner driver: {}", bets.size(), event.getEventId(), event.getWinnerDriverId());

        for (final BetDto bet : bets) {
            try {
                betsService.finishBet(bet.getId(), event.getWinnerDriverId());
                log.debug("Successfully finished bet with id: {} for event: {}, winner driver: {}", bet.getId(), event.getEventId(), event.getWinnerDriverId());
            } catch (Exception e) {
                log.error("Failed to finish bet with id: {} for event: {}, winner driver: {}, error: {}",
                        bet.getId(), event.getEventId(), event.getWinnerDriverId(), e.getMessage(), e);
            }
        }

        log.info("Processed {} bets for finished event with id: {}, winner driver: {}", bets.size(), event.getEventId(), event.getWinnerDriverId());
    }
}