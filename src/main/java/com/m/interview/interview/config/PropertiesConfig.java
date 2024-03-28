package com.m.interview.interview.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "drones")
@Data
public class PropertiesConfig {
    /**
     * Schedule dispatcher checking interval every () Ms. From 100ms to 24 hours
     */
    @Min(100)
    @Max(24 * 60 * 60 * 1000)
    private long dispatcherCheckMs;

    /**
     * Discharge drone on every move by ()%
     */
    @Min(0)
    @Max(100)
    private int dischargeWhenMovePercent;

    /**
     * Discharge Idle drone on every dispatcher check by ()%. 0 - for disable
     */
    @Min(0)
    @Max(100)
    private int dischargeWhenIdlePercent;

    /**
     * Charging speed. Default - 5% per dispatcher check
     */
    @Min(0)
    @Max(100)
    private int chargeOnEveryCheckByPercent;

    /**
     * Threshold of drone's battery level. Drone will be sent to charging station and will not be available for deliveries. Default 25%
     */
    @Min(0)
    @Max(100)
    private int batteryLevelThreshold;

    /**
     * Step on charging required before drone become available for deliveries. Min 1
     */
    @Min(1)
    private int stepsBeforeCharged;
}
