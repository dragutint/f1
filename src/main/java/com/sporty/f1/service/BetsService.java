package com.sporty.f1.service;

import com.sporty.f1.domain.BetEntity;
import com.sporty.f1.domain.BetStatusEnum;
import com.sporty.f1.domain.UserEntity;
import com.sporty.f1.dto.BetDto;
import com.sporty.f1.dto.BetRequest;
import com.sporty.f1.dto.DriverDto;
import com.sporty.f1.dto.EventDto;
import com.sporty.f1.exceptions.BadRequestException;
import com.sporty.f1.repository.BetRepository;
import com.sporty.f1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetsService {

    private final F1ProviderService f1ProviderService;
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final BetValidator betValidator;

    @Transactional
    public void create(BetRequest request) {

        log.info("Request to place bet: {}", request);

        betValidator.validateRequest(request);

        final UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found with id: " + request.getUserId()));

        if (user.getBalance() < request.getAmount()) {
            throw new BadRequestException("Insufficient balance, couldn't place a bet for user " + user.getId());
        }

        // check if the event and driver exist
        final EventDto eventDto = f1ProviderService.getEventById(request.getEventId());
        if (eventDto == null) {
            throw new BadRequestException("Event not found with id: " + request.getEventId());
        }
        final DriverDto driverDto = eventDto.getDrivers().stream()
                .filter(driver -> driver.getId().equals(request.getDriverId()))
                .findFirst()
                .orElse(null);
        if (driverDto == null) {
            throw new BadRequestException("Driver not found with id: " + request.getDriverId() + " for event: " + request.getEventId());
        }

        log.debug("Bet request {} validation passed, going to persist the bet and update user balance, user: {}", request, user);

        // save the bet to the database
        final BetEntity bet = new BetEntity();
        bet.setAmount(request.getAmount());
        bet.setUser(user);
        bet.setEventId(request.getEventId());
        bet.setDriverId(request.getDriverId());
        bet.setOdd(driverDto.getOdd());
        bet.setStatus(BetStatusEnum.PLACED);
        betRepository.save(bet);

        user.setBalance(user.getBalance() - request.getAmount());
        userRepository.save(user);

        log.info("Bet placed successfully: {}", bet);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void finishBet(UUID betId, String winnerDriverId) {

        log.info("Finishing bet with id: {}, winnerDriverId: {}", betId, winnerDriverId);

        final BetEntity bet = betRepository.lockById(betId)
                .orElseThrow(() -> new BadRequestException("Bet not found with id: " + betId));

        if (bet.getStatus() != BetStatusEnum.PLACED) {
            throw new BadRequestException("Bet with id: " + betId + " is not in PLACED status, cannot finish it, current status: " + bet.getStatus());
        }

        if (bet.getDriverId().equals(winnerDriverId)) {
            bet.setStatus(BetStatusEnum.WON);
            final UserEntity user = bet.getUser();
            user.setBalance(user.getBalance() + (bet.getAmount() * bet.getOdd()));
            userRepository.save(user);
            log.debug("Bet with id: {} finished as WON, updated user balance", betId);
        } else {
            bet.setStatus(BetStatusEnum.LOST);
            log.debug("Bet with id: {} finished as LOST", betId);
        }

        betRepository.save(bet);

        log.info("Bet finished successfully: {}", bet);
    }

    @Transactional(readOnly = true)
    public List<BetDto> findByEventIdAndStatus(String eventId, BetStatusEnum status) {

        log.info("Finding bets for eventId: {}", eventId);

        final List<BetEntity> bets = betRepository.findByEventIdAndStatus(eventId, status);

        if (bets.isEmpty()) {
            log.warn("No bets found for eventId: {}", eventId);
            return List.of();
        }

        return bets.stream()
                .map(this::mapBet)
                .toList();
    }

    // private

    private BetDto mapBet(BetEntity betEntity) {

        return BetDto.builder()
                .id(betEntity.getId())
                .build();
    }
}
