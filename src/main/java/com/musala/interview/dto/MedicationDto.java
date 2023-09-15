package com.musala.interview.dto;

import lombok.Data;

@Data
public class MedicationDto {
    /**
     * Medication name (allowed only letters, numbers, ‘-‘, ‘_’)
     */
    private String name;
    /**
     * Weight, integer, max 500 - not more than any drone capacity
     */
    private int weight;
    /**
     * Code (allowed only upper case letters, underscore and numbers);
     */
    private String code;
    /**
     * Image - image id in DB (JPEG)
     */
    private long imageId;
}
