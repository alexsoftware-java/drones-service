package com.m.interview.interview.controller;

import com.m.interview.interview.dto.Model;
import com.m.interview.interview.dto.State;
import com.m.interview.interview.entity.DroneEntity;
import com.m.interview.interview.repository.DronesRepository;
import com.m.interview.interview.service.DispatcherScheduledService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests.
 */
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@MockBean(DispatcherScheduledService.class) // disable scheduler by mocking it
@AutoConfigureMockMvc
class DronesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private DronesRepository dronesRepository;
    private DroneEntity droneEntity;

    @BeforeEach
    void init(){
        droneEntity = new DroneEntity();
        droneEntity.setModel(Model.CRUISERWEIGHT);
        droneEntity.setSerialNumber("DRN_123456");
        droneEntity.setState(State.IDLE);
        droneEntity.setBatteryCapacity(100);
        droneEntity.setWeightLimit(300);
    }

    @Test
    void addDrone() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "serialNumber": "DRONE_1",
                                  "name": "Delivery Drone 1",
                                  "model": "Cruiserweight",
                                  "weightLimit": 250
                                }"""))
        // then
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        {"serialNumber":"DRONE_1","model":"Cruiserweight","weightLimit":250,"batteryCapacity":100,"state":"IDLE"}"""));
    }

    @Test
    void addDroneBadRequest() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "serialNumber": "#$!",
                                  "name": "Delivery Drone 1",
                                  "model": "Cruiserweight",
                                  "weightLimit": 250
                                }"""))
        // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string("""
                        Error occurred, request validation failed! addDrone.droneRequestDto: - serial number (100 characters max);
                        - model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
                        - weight limit (500gr max);"""));
    }

    @Test
    void deleteDrone() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        // when
        mockMvc.perform(delete("/api/v1/drones/DRN_123456"))
        // then
                .andExpect(status().isOk());
        verify(dronesRepository).delete(any(DroneEntity.class));
    }

    @Test
    void deleteDroneBadRequest() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        // when
        mockMvc.perform(delete("/api/v1/drones/RN_123456"))
                // then
                .andExpect(status().isBadRequest());
        verify(dronesRepository, times(0)).delete(any(DroneEntity.class));
    }

    @Test
    void listAvailableDrones() throws Exception {
        dronesRepository.save(droneEntity);
        mockMvc.perform(get("/api/v1/drones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("""
                        {"serialNumber":"DRN_123456","model":"Cruiserweight","weightLimit":300,"batteryCapacity":100,"state":"IDLE"}""")));
    }

    @Test
    void getDroneBatteryLevelBySN() throws Exception {
        dronesRepository.save(droneEntity);
        mockMvc.perform(get("/api/v1/drones/DRN_123456/battery"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("100"));

    }
}