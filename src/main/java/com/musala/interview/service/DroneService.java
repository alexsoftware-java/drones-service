package com.musala.interview.service;

import com.musala.interview.converter.DroneEntityToDroneDtoConverter;
import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.State;
import com.musala.interview.entity.DroneEntity;
import com.musala.interview.exception.DispatcherException;
import com.musala.interview.repository.DronesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DroneService {
    private final DronesRepository dronesRepository;
    private final DroneEntityToDroneDtoConverter converter;

    /**
     * @return List of drones in IDLE state
     */
    public List<DroneDto> getAvailableDrones() {
        var dronesInIdleState = dronesRepository.findByStateIn(List.of(State.IDLE));
        log.debug("Get available drones request. Found {} drones in IDLE state", dronesInIdleState.size());
        if (!dronesInIdleState.isEmpty()) {
            return dronesInIdleState.stream().map(converter::convert).toList();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Creates drone entity
     *
     * @param requestDto validated by {@link com.musala.interview.validator.DroneValidator}
     * @return created drone
     */
    public DroneDto addDrone(DroneDto requestDto) {
        log.debug("Add drone request, request body {}", requestDto);
        var drone = new DroneEntity();
        drone.setSerialNumber(requestDto.getSerialNumber());
        drone.setModel(requestDto.getModel());
        // Get drone's battery level, if null - default 100% will be set by hibernate
        if (requestDto.getBatteryCapacity() != null) {
            drone.setBatteryCapacity(requestDto.getBatteryCapacity());
        }
        // Get state, if null - default IDLE will be set by hibernate
        if (requestDto.getState() != null) {
            drone.setState(requestDto.getState());
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
}