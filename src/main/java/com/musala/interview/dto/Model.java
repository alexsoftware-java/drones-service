package com.musala.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Model {
    Lightweight(100),
    Middleweight( 200),
    Cruiserweight(400),
    Heavyweight(500);

    /**
    Each model has max weight limit
    */
    @Getter
    private final int weightLimit;
}
