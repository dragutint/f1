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
public class Session {

    @JsonProperty("meeting_key")
    private Integer meetingKey;

    @JsonProperty("session_key")
    private Integer sessionKey;

    @JsonProperty("location")
    private String location;

    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;

    @JsonProperty("session_type")
    private String sessionType;

    @JsonProperty("session_name")
    private String sessionName;

    @JsonProperty("country_key")
    private Integer countryKey;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("circuit_key")
    private Integer circuitKey;

    @JsonProperty("circuit_short_name")
    private String circuitShortName;

    @JsonProperty("gmt_offset")
    private String gmtOffset;

    @JsonProperty("year")
    private Integer year;

}
