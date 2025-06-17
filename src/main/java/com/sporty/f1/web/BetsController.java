package com.sporty.f1.web;

import com.sporty.f1.service.BetsService;
import com.sporty.f1.dto.BetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/formula/v1/bets")
@RequiredArgsConstructor
@RestController
public class BetsController {

    private final BetsService betsService;

    @PostMapping
    public ResponseEntity<Void> placeBet(@RequestBody BetRequest request) {

        betsService.create(request);
        return ResponseEntity.ok().build();
    }

}
