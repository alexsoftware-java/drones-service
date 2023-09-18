package com.musala.interview.dto;

import com.musala.interview.validator.ValidEnum;
import lombok.Data;

/**
 * Class represents Drone
 */
@Data
public class DroneDto {
    /**
     * Serial number (100 characters max);
     */
    private String serialNumber;
    /**
     * Model (Lightweight, Middleweight, Cruiserweight, Heavyweight)
     */
    @ValidEnum(allowedValues = "LIGHTWEIGHT|MIDDLEWEIGHT|CRUISERWEIGHT|HEAVYWEIGHT", message = "Invalid drone model! Allowed: Lightweight, Middleweight, Cruiserweight, Heavyweight")
    private Model model;
    /**
     * Weight limit (500gr max), 0-500
     */
    private Integer weightLimit;
    /**
     * Battery capacity (percentage), 0-100
     */
    private Integer batteryCapacity;
    /**
     * State (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)
     */
    @ValidEnum(allowedValues = "IDLE|LOADING|LOADED|DELIVERING|DELIVERED|RETURNING", message = "Invalid state! Allowed: IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING")
    private State state;
}
