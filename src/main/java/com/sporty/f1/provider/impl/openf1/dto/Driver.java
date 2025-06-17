package com.sporty.f1.provider.impl.openf1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {

    @JsonProperty("broadcast_name")
    private String broadcastName;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("driver_number")
    private Integer driverNumber;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("headshot_url")
    private String headshotUrl;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("meeting_key")
    private Integer meetingKey;

    @JsonProperty("name_acronym")
    private String nameAcronym;

    @JsonProperty("session_key")
    private Integer sessionKey;

    @JsonProperty("team_colour")
    private String teamColour;

    @JsonProperty("team_name")
    private String teamName;

}
