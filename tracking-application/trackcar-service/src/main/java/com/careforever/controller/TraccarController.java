package com.careforever.controller;

import com.careforever.dto.DeviceDto;
import com.careforever.dto.PositionDto;
import com.careforever.service.TraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<PositionDto> getPositions(){

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
    public List<PositionDto> getRoute(@RequestParam Long deviceId, @RequestParam String from, @RequestParam String to){

        return traccarService.getRoute(deviceId, from, to);

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
