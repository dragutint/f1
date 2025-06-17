package com.sporty.f1.service;

import com.sporty.f1.exceptions.BadRequestException;
import com.sporty.f1.dto.BetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BetValidator {

    public void validateRequest(BetRequest request) {
        if (request == null) {
            throw new BadRequestException("Bet request must be provided");
        }

        if (request.getUserId() == null) {
            throw new BadRequestException("User must be provided");
        }

        if (StringUtils.isBlank(request.getDriverId())) {
            throw new BadRequestException("Driver must be provided");
        }

        if (StringUtils.isBlank(request.getEventId())) {
            throw new BadRequestException("Event must be provided");
        }
    }
}
