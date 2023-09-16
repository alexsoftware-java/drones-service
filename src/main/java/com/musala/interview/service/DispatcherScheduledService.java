package com.musala.interview.service;

import com.musala.interview.config.PropertiesConfig;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.repository.DronesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class DispatcherScheduledService {
    private final DronesRepository dronesRepository;
    private final PropertiesConfig propertiesConfig;
    private final static Map<State, State> STATES_MAP = new HashMap<>(6);

    static {
        STATES_MAP.put(State.IDLE, State.LOADING);
        STATES_MAP.put(State.LOADING, State.LOADED);
        STATES_MAP.put(State.LOADED, State.DELIVERING);
        STATES_MAP.put(State.DELIVERING, State.DELIVERED);
        STATES_MAP.put(State.DELIVERED, State.RETURNING);
        STATES_MAP.put(State.RETURNING, State.IDLE);
    }

    private final Map<String, Integer> dronesOnCharge = new HashMap<>(10);

    /**
     * Drone dispatcher:
     * 1) Monitors drone states and make them moves every specified number of ms.
     * 2) Monitors batteries level, when it's too low - drone can't start moving before charged necessary
     * 3) Every step is discharging drone by 5%. Step in the idle state - by 2%.
     */
    @Scheduled(fixedRateString = "${drones.dispatcher-check-ms}")
    public void dronesDispatcher() {
        var drones = dronesRepository.findAll();
        log.debug("Dispatcher: I'm alive! Drones in the list {}", drones.stream().map(DroneEntity::getSerialNumber).toList());
        for (var drone : drones) {
            String sn = drone.getSerialNumber();
            // Check if drone is still charging (charging steps is not over)
            if (dronesOnCharge.containsKey(sn) && dronesOnCharge.get(sn) > 0) {
                log.debug("Dispatcher: Drone {} is still on charge, steps remaining: {}", sn, dronesOnCharge.get(sn));
                charge(drone);
                dronesOnCharge.put(sn, dronesOnCharge.get(sn) - 1);
            // Check if drone is needs to be charged
            } else if (drone.getState().equals(State.IDLE) && drone.getBatteryCapacity() < propertiesConfig.getBatteryLevelThreshold()) {
                dronesOnCharge.put(drone.getSerialNumber(), propertiesConfig.getStepsBeforeCharged());
                log.warn("Dispatcher: Drone {} has low battery capacity level! Going to send it to charging station!", sn);
                charge(drone);
            } else {
                move(drone);
            }
        }
    }

    /**
     * Drone's mover, change drone state if it has a delivery, or returning. And discharging drone on every step
     * @param drone DroneEntity
     */
    private void move(DroneEntity drone) {
        State previousState = drone.getState();
        // Need to change state to next one if drone has some goods on board or if it's going back
        if (!drone.getGoods().isEmpty() || previousState.equals(State.RETURNING)) {
            var newDroneState = STATES_MAP.get(previousState);
            // Goods have been delivered - we don't need to store them anymore
            if (previousState.equals(State.DELIVERED)) {
                drone.getGoods().removeAll(drone.getGoods());
            }
            drone.setState(newDroneState);
            drone.setBatteryCapacity(drone.getBatteryCapacity() - propertiesConfig.getDischargeWhenMovePercent());
            log.info("Dispatcher: Drone {} has been moved from {} to {}", drone.getSerialNumber(), previousState, newDroneState);
        } else {
            // Anyway drones are discharging even when IDLE (depends on config)
            drone.setBatteryCapacity(drone.getBatteryCapacity() - propertiesConfig.getDischargeWhenIdlePercent());
        }
        dronesRepository.saveAndFlush(drone);
        log.info("Dispatcher: Time is going, drone {} has been discharged up to {}%", drone.getSerialNumber(), drone.getBatteryCapacity());
    }

    private void charge(DroneEntity drone) {
        int newBatteryLevel = drone.getBatteryCapacity() + propertiesConfig.getChargeOnEveryCheckByPercent();
        if (newBatteryLevel > 100) newBatteryLevel = 100;
        drone.setBatteryCapacity(newBatteryLevel);
        log.info("Dispatcher: Drone is on charge, current battery level is {}%", drone.getBatteryCapacity());
        dronesRepository.saveAndFlush(drone);
    }

}
