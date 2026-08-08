package com.careforever.service;

import com.careforever.client.TraccarClient;
import com.careforever.dto.DeviceDto;
import com.careforever.dto.PositionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraccarService {

    private final TraccarClient traccarClient;

    public List<DeviceDto> getDevices(){

        return traccarClient.getDevices();

    }

    public List<PositionDto> getPositions(){

        return traccarClient.getPositions();

    }

    public String getEvents(){

        return traccarClient.getEvents();

    }

    public String getGeofences(){

        return traccarClient.getGeofences();

    }

    public String getDrivers(){

        return traccarClient.getDrivers();

    }

    public String getGroups(){

        return traccarClient.getGroups();

    }

    public List<PositionDto> getRoute(Long deviceId, String from, String to){

        return traccarClient.getRoute(deviceId, from, to);

    }

    public String getTrips(){

        return traccarClient.getTrips();

    }

    public String getStops(){

        return traccarClient.getStops();

    }

}
