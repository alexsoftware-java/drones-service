package com.musala.interview.controller;

import com.musala.interview.dto.Model;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.repository.DronesRepository;
import com.musala.interview.service.DispatcherScheduledService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
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
 * data.sql applies.
 * No DB clean applies
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@MockBean(DispatcherScheduledService.class) // disable scheduler by mocking it
@AutoConfigureMockMvc
class MedicationsControllerTest {

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
    void addMedication() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        // when
        mockMvc.perform(post("/api/v1/drones/DRN_123456/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                       "name":"ASPIRIN",
                                       "weight":100,
                                       "code":"ASP_1"
                                }"""))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        [{"id":5,"name":"ASPIRIN","weight":100,"code":"ASP_1","imageId":null}]"""));

    }

    @Test
    void getMedication() {
    }

    @Test
    void addImage() {
    }

    @Test
    void getImage() {
    }
}