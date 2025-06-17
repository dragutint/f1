package com.sporty.f1.provider.impl.openf1;

import com.sporty.f1.provider.impl.openf1.dto.Driver;
import com.sporty.f1.provider.impl.openf1.dto.Session;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "open-f1-feign-client", url = "https://api.openf1.org/", path = "/v1")
public interface OpenF1RemoteClient {

    @GetMapping("/sessions")
    List<Session> getSessions(
            @RequestParam(name = "session_type", required = false) String sessionType,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "country_code", required = false) String countryCode,
            @RequestParam(name = "session_key", required = true) String sessionKey
    );

    @GetMapping("/drivers")
    List<Driver> getDrivers(@RequestParam(name = "session_key", required = true) String sessionKey,
                            @RequestParam(name = "driver_number", required = true) String driverNumber);

}
