package com.musala.interview.service;

import com.musala.interview.converter.GoodsEntityToMedicationDtoConverter;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.entity.GoodsEntity;
import com.musala.interview.exception.DispatcherException;
import com.musala.interview.repository.DronesRepository;
import com.musala.interview.repository.GoodsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class MedicationServiceTest {
    @Mock
    private GoodsRepository goodsRepository;
    @Mock
    private DronesRepository dronesRepository;

    private MedicationService medicationService;
    private MedicationDto medicationRequest;
    private GoodsEntity medicationEntity;

    @BeforeEach
    void init(){
        medicationService = new MedicationService(goodsRepository, dronesRepository, new GoodsEntityToMedicationDtoConverter());

        medicationRequest = new MedicationDto();
        medicationRequest.setName("Aspirin");
        medicationRequest.setCode("ASP");
        medicationRequest.setWeight(100);

        medicationEntity = new GoodsEntity();
        medicationEntity.setId(2);
        medicationEntity.setName("Pills");
        medicationEntity.setCode("PIL_1");
        medicationEntity.setWeight(50);
    }

    @Test
    void getDroneMedication() {
        // given
        var medication = new GoodsEntity();
        medication.setId(1);
        medication.setName("Aspirin");
        medication.setCode("ASP");
        medication.setWeight(100);
        // then
        when(goodsRepository.findByDroneSerialNumber(anyString())).thenReturn(Optional.of(List.of(medication, medicationEntity)));
        // when
        var result = medicationService.getDroneMedication("12345");
        assertEquals(2, result.size());
        assertEquals("Aspirin", result.get(0).getName());
        assertEquals("ASP", result.get(0).getCode());
        assertEquals(100, result.get(0).getWeight());
    }

    @Test
    void addMedication() {
        // given
        when(goodsRepository.findByDroneSerialNumber(anyString())).thenReturn(Optional.of(List.of(medicationEntity)));
        var drone = new DroneEntity();
        drone.setState(State.IDLE);
        drone.setWeightLimit(300);
        when(dronesRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(drone));
        when(goodsRepository.saveAndFlush(any(GoodsEntity.class))).thenReturn(medicationEntity);
        // when
        var result = medicationService.addMedication("12345", medicationRequest);
        verify(goodsRepository, times(2)).findByDroneSerialNumber(anyString());
        verify(dronesRepository, times(1)).findBySerialNumber(anyString());
        verify(goodsRepository, times(1)).saveAndFlush(any(GoodsEntity.class));
        assertEquals(1, result.size());
        assertEquals("Pills", result.get(0).getName());
    }

    @Test
    void addMedicationThrowsExceptionWhenLimitExceeded() {
        // given
        when(goodsRepository.findByDroneSerialNumber(anyString())).thenReturn(Optional.of(List.of(medicationEntity)));
        var drone = new DroneEntity();
        drone.setState(State.IDLE);
        drone.setWeightLimit(10);
        when(dronesRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(drone));
        when(goodsRepository.saveAndFlush(any(GoodsEntity.class))).thenReturn(medicationEntity);
        // when
        var ex = assertThrows(DispatcherException.class, () -> medicationService.addMedication("12345", medicationRequest));
        assertEquals("Medication can't be added to drone with SN 12345, as weight limit of 10 gr will be exceeded!", ex.getMessage());
    }

    @Test
    void addMedicationThrowsExceptionWhenDroneStatusNotIdle() {
        // given
        when(goodsRepository.findByDroneSerialNumber(anyString())).thenReturn(Optional.of(List.of(medicationEntity)));
        var drone = new DroneEntity();
        drone.setState(State.DELIVERING);
        drone.setWeightLimit(300);
        when(dronesRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(drone));
        when(goodsRepository.saveAndFlush(any(GoodsEntity.class))).thenReturn(medicationEntity);
        // when
        var ex = assertThrows(DispatcherException.class, () -> medicationService.addMedication("12345", medicationRequest));
        assertEquals("Drone with SN 12345 is not available! Medication can't be added", ex.getMessage());
    }
}