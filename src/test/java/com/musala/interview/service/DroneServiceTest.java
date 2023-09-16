package com.musala.interview.service;

import com.musala.interview.config.PropertiesConfig;
import com.musala.interview.converter.DroneEntityToDroneDtoConverter;
import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.Model;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.repository.DronesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class DroneServiceTest {

    @Mock
    private DronesRepository dronesRepository;
    private DroneService service;
    private DroneEntity entity;

    @BeforeEach
    void init(){
        service = new DroneService(dronesRepository, new DroneEntityToDroneDtoConverter(), new PropertiesConfig());
        entity = new DroneEntity();
        entity.setId(1);
        entity.setSerialNumber("12345");
        entity.setState(State.IDLE);
        entity.setModel(Model.MIDDLEWEIGHT);
        entity.setWeightLimit(250);
    }

    @Test
    void getAvailableDrones() {
        // given
        when(dronesRepository.findByStateIn(eq(List.of(State.IDLE)))).thenReturn(List.of(entity));

        // when
        var resultList = assertDoesNotThrow(() -> service.getAvailableDrones());

        // then
        assertFalse(resultList.isEmpty());
        assertEquals(1, resultList.size());
        assertEquals("12345", resultList.get(0).getSerialNumber());
        assertEquals(State.IDLE, resultList.get(0).getState());
        assertEquals(Model.MIDDLEWEIGHT, resultList.get(0).getModel());
    }

    @Test
    void getAvailableDronesReturnsEmptyList() {
        // given
        when(dronesRepository.findByStateIn(eq(List.of(State.IDLE)))).thenReturn(Collections.emptyList());
        // when
        var resultList = assertDoesNotThrow(() -> service.getAvailableDrones());
        // then
        assertTrue(resultList.isEmpty());
    }

    @Test
    void addDrone() {
        // given
        when(dronesRepository.saveAndFlush(any(DroneEntity.class))).thenReturn(entity);
        var request = new DroneDto();
        request.setSerialNumber("12345");
        request.setModel(Model.MIDDLEWEIGHT);
        request.setWeightLimit(250);
        // when
        var result = assertDoesNotThrow(() -> service.addDrone(request));
        // then
        verify(dronesRepository, times(1)).saveAndFlush(any(DroneEntity.class));
        assertEquals(request.getSerialNumber(), result.getSerialNumber());
        assertEquals(request.getModel(), result.getModel());
        assertEquals(request.getWeightLimit(), result.getWeightLimit());
    }

    @Test
    void getDroneBatteryLevelBySN() {
        when(dronesRepository.findBySerialNumber(eq("12345"))).thenReturn(Optional.of(entity));
        assertEquals(entity.getBatteryCapacity(), service.getDroneBatteryLevelBySN("12345"));
    }
}