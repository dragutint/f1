package com.sporty.f1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetRequest {

    private Integer userId;
    private String eventId;
    private String driverId;
    private Double amount;
}
