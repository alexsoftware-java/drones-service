package com.musala.interview.dto;

import lombok.Data;

/**
 * Class represents Drone
 */
@Data
public class DroneDto {
    /**
     * Serial number (100 characters max);
     */
    private String sn;
    /**
     * Model (Lightweight, Middleweight, Cruiserweight, Heavyweight)
     */
    private Model model;
    /**
     * Weight limit (500gr max), integer, 0-500
     */
    private int weightLimit;
    /**
     * battery capacity (percentage), integer, 0-100. Default = 100
     */
    private int batteryCapacity = 100;
    /**
     * State (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING). Default - IDLE
     */
    private State state = State.IDLE;
}
