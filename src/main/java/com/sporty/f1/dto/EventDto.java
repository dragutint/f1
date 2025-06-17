package com.sporty.f1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {

    private String id;
    private String location;
    private String startDate;
    private String endDate;
    private String type;
    private String name;
    private Integer year;
    private String countryCode;
    private List<DriverDto> drivers;

}
