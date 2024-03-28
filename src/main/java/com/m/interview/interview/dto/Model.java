package com.m.interview.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.m.interview.interview.utils.Constants.MAX_DRONE_CAPACITY;

/**
 * Enum represents supported models of drones. Each model has weight limit and user-defined name for deserialization
 */
@AllArgsConstructor
public enum Model {
    @JsonProperty("Lightweight")
    LIGHTWEIGHT(100),
    @JsonProperty("Middleweight")
    MIDDLEWEIGHT( 200),
    @JsonProperty("Cruiserweight")
    CRUISERWEIGHT(400),
    @JsonProperty("Heavyweight")
    HEAVYWEIGHT(MAX_DRONE_CAPACITY);

    @Getter
    private final int weightLimit;
}
