package com.m.interview.interview.service;

import com.m.interview.interview.config.PropertiesConfig;
import com.m.interview.interview.converter.DroneEntityToDroneDtoConverter;
import com.m.interview.interview.dto.DroneDto;
import com.m.interview.interview.dto.State;
import com.m.interview.interview.entity.DroneEntity;
import com.m.interview.interview.exception.DispatcherException;
import com.m.interview.interview.repository.DronesRepository;
import com.m.interview.interview.validator.DroneValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DroneService {
    private final DronesRepository dronesRepository;
    private final DroneEntityToDroneDtoConverter converter;
    private final PropertiesConfig propertiesConfig;

    /**
     * @return List of drones in IDLE state and having good level of battery charge
     */
    public List<DroneDto> getAvailableDrones() {
        var dronesInIdleState = dronesRepository.findByStateIn(List.of(State.IDLE))
                .stream()
                .filter(drone -> drone.getBatteryCapacity() > propertiesConfig.getBatteryLevelThreshold())
                .toList();
        log.debug("Get available drones request. Found {} drones in IDLE state and >25% of battery", dronesInIdleState.size());
        if (!dronesInIdleState.isEmpty()) {
            return dronesInIdleState.stream().map(converter::convert).toList();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Creates drone entity
     *
     * @param requestDto validated by {@link DroneValidator}
     * @return created drone
     */
    public DroneDto addDrone(DroneDto requestDto) {
        log.debug("Add drone request, request body {}", requestDto);
        var drone = new DroneEntity();
        drone.setSerialNumber(requestDto.getSerialNumber());
        drone.setModel(requestDto.getModel());
        // Get drone's battery level, if null - default 100% will be set
        if (requestDto.getBatteryCapacity() != null) {
            drone.setBatteryCapacity(requestDto.getBatteryCapacity());
        } else {
            drone.setBatteryCapacity(100);
        }
        // Get state, if null - default IDLE will be set
        if (requestDto.getState() != null) {
            drone.setState(requestDto.getState());
        } else {
            drone.setState(State.IDLE);
        }
        // Get weight limit, if null - limit of drone's model will be used
        if (requestDto.getWeightLimit() != null) {
            drone.setWeightLimit(requestDto.getWeightLimit());
        } else {
            drone.setWeightLimit(requestDto.getModel().getWeightLimit());
        }
        var createdDrone = dronesRepository.saveAndFlush(drone);
        log.debug("Drone has been added with id {}", createdDrone.getId());
        return converter.convert(createdDrone);
    }

    public Integer getDroneBatteryLevelBySN(String serialNumber) {
        var drone = dronesRepository.findBySerialNumber(serialNumber);
        if (drone.isEmpty()) {
            throw new DispatcherException("Drone with SN %s not found".formatted(serialNumber));
        }
        return drone.get().getBatteryCapacity();
    }

    public void deleteDrone(String serialNumber) {
        var drone = dronesRepository.findBySerialNumber(serialNumber);
        if (drone.isEmpty()) {
            throw new DispatcherException("Drone with SN %s not found".formatted(serialNumber));
        }
        log.debug("Drone {} will be deleted by user request", serialNumber);
        dronesRepository.delete(drone.get());
        dronesRepository.flush();
    }
}
