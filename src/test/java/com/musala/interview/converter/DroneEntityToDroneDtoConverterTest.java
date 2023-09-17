package com.musala.interview.converter;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.Model;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

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