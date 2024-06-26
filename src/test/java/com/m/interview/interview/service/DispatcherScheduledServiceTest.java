package com.m.interview.interview.service;

import com.m.interview.interview.config.PropertiesConfig;
import com.m.interview.interview.dto.State;
import com.m.interview.interview.entity.DroneEntity;
import com.m.interview.interview.entity.GoodsEntity;
import com.m.interview.interview.repository.DronesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DispatcherScheduledServiceTest {
    @MockBean
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