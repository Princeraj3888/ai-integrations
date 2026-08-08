package com.careforever.dto;

import lombok.Data;

@Data
public class AttributesDto {

    private int priority;

    private int sat;

    private int event;

    private boolean ignition;

    private boolean motion;

    private int rssi;

    private int io200;

    private int io69;

    private double pdop;

    private double hdop;

    private double power;

    private double battery;

    private int io68;

    private int operator;

    private long odometer;

    private double distance;

    private double totalDistance;

    private long hours;
}
