package com.musala.interview.service;

import com.musala.interview.config.PropertiesConfig;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.entity.GoodsEntity;
import com.musala.interview.repository.DronesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class DispatcherScheduledServiceTest {
    @Mock
    private DronesRepository repository;
    private DispatcherScheduledService service;
    private DroneEntity drone;
    private PropertiesConfig config;

    @BeforeEach
    void init(){
        drone = new DroneEntity();
        drone.setSerialNumber("12345");
        drone.setBatteryCapacity(100);
        drone.setState(State.IDLE);
        var goods = new ArrayList<GoodsEntity>();
        goods.add(new GoodsEntity());
        drone.setGoods(goods);
        config = new PropertiesConfig();
        config.setBatteryLevelThreshold(25);
        config.setDischargeWhenIdlePercent(2);
        config.setStepsBeforeCharged(5);
        config.setChargeOnEveryCheckByPercent(5);
        config.setDischargeWhenMovePercent(5);
    }

    @Test
    void dronesDispatcherTest() {
        // given - drone scheduler service config
        service = new DispatcherScheduledService(repository, config);
        when(repository.findAll()).thenReturn(List.of(drone));

        // when
        assertDoesNotThrow(() -> service.dronesDispatcher());

        // then drone in the list should change the state and battery level
        ArgumentCaptor<DroneEntity> newEntity = ArgumentCaptor.forClass(DroneEntity.class);
        verify(repository, times(1)).saveAndFlush(newEntity.capture());
        assertEquals(State.LOADING, newEntity.getValue().getState());
        assertEquals(95, newEntity.getValue().getBatteryCapacity());
    }

    @Test
    void dronesDispatcherTestAfterTwoSteps() {
        // given - drone scheduler service config
        service = new DispatcherScheduledService(repository, config);
        when(repository.findAll()).thenReturn(List.of(drone));

        // when scheduler started 2 times
        assertDoesNotThrow(() -> service.dronesDispatcher());
        assertDoesNotThrow(() -> service.dronesDispatcher());

        // then drone in the list should change the state and battery level
        ArgumentCaptor<DroneEntity> newEntity = ArgumentCaptor.forClass(DroneEntity.class);
        verify(repository, times(2)).saveAndFlush(newEntity.capture());
        assertEquals(State.LOADED, newEntity.getValue().getState());
        assertEquals(90, newEntity.getValue().getBatteryCapacity());
    }

    @Test
    void dronesDispatcherTestOnDelivered() {
        // given - drone scheduler service config
        service = new DispatcherScheduledService(repository, config);
        when(repository.findAll()).thenReturn(List.of(drone));

        // when scheduler started 5 times
        service.dronesDispatcher();
        service.dronesDispatcher();
        service.dronesDispatcher();
        service.dronesDispatcher();
        service.dronesDispatcher();

        // then drone in the list should change the state and battery level
        ArgumentCaptor<DroneEntity> newEntity = ArgumentCaptor.forClass(DroneEntity.class);
        verify(repository, times(5)).saveAndFlush(newEntity.capture());
        assertEquals(State.RETURNING, newEntity.getValue().getState());
        assertEquals(75, newEntity.getValue().getBatteryCapacity());
        // and goods list should be empty
        assertEquals(Collections.emptyList(), newEntity.getValue().getGoods());
    }
}