package com.careforever.controller;

import com.careforever.dto.DeviceDto;
import com.careforever.service.TraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/traccar")
@RequiredArgsConstructor
public class TraccarController {

    private final TraccarService traccarService;

    @GetMapping("/devices")
    public List<DeviceDto> devices(){

        return traccarService.getDevices();

    }

    @GetMapping("/positions")
    public String getPositions(){

        return traccarService.getPositions();

    }
    @GetMapping("/geofences")
    public String getGeofences(){

        return traccarService.getGeofences();

    }
    @GetMapping("/drivers")
    public String getDrivers(){

        return traccarService.getDrivers();

    }
    @GetMapping("/groups")
    public String getGroups(){

        return traccarService.getGroups();

    }
    @GetMapping("/reports/route")
    public String getRoute(){

        return traccarService.getRoute();

    }
    @GetMapping("/reports/trips")
    public String getTrips(){

        return traccarService.getTrips();

    }
    @GetMapping("/reports/stops")
    public String getStops(){

        return traccarService.getStops();

    }
    @GetMapping("/events")
    public String getEvents(){

        return traccarService.getEvents();

    }

}
