package com.careforever.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.List;

@Data
public class PositionDto {

    private long id;

    private Map<String, Object> attributes;

    private long deviceId;

    private String protocol;

    private OffsetDateTime serverTime;

    private OffsetDateTime deviceTime;

    private OffsetDateTime fixTime;

    private boolean valid;

    private double latitude;

    private double longitude;

    private double altitude;

    private double speed;

    private double course;

    private String address;

    private double accuracy;

    private Object network;

    private List<Long> geofenceIds;
}
