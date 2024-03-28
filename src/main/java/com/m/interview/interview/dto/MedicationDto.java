package com.m.interview.interview.dto;

import lombok.Data;

@Data
public class MedicationDto {
    /**
     * Medication ID
     */
    private Long id;
    /**
     * Medication name (allowed only letters, numbers, ‘-‘, ‘_’)
     */
    private String name;
    /**
     * Weight, max 500 - not more than any drone capacity
     */
    private int weight;
    /**
     * Code (allowed only upper case letters, underscore and numbers);
     */
    private String code;
    /**
     * Image - image id in DB (JPEG)
     */
    private Long imageId;
}
