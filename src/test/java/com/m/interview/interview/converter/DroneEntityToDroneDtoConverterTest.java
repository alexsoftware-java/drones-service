package com.m.interview.interview.converter;

import com.m.interview.interview.dto.DroneDto;
import com.m.interview.interview.dto.Model;
import com.m.interview.interview.dto.State;
import com.m.interview.interview.entity.DroneEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DroneEntityToDroneDtoConverterTest {
    private final DroneEntityToDroneDtoConverter converter = new DroneEntityToDroneDtoConverter();
    private DroneDto dto;
    private DroneEntity entity;

    @BeforeEach
    void init(){
        dto = new DroneDto();
        dto.setSerialNumber("12345");
        dto.setBatteryCapacity(55);
        dto.setWeightLimit(200);
        dto.setModel(Model.CRUISERWEIGHT);
        dto.setState(State.DELIVERED);
        entity = new DroneEntity();
        entity.setSerialNumber("12345");
        entity.setBatteryCapacity(55);
        entity.setWeightLimit(200);
        entity.setModel(Model.CRUISERWEIGHT);
        entity.setState(State.DELIVERED);
    }

    @Test
    void converterTest() {
        // when
        var resultDto = converter.convert(entity);
        // then
        assertEquals(dto, resultDto);
    }
}