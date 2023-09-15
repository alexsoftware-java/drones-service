package com.musala.interview.controller;

import com.musala.interview.controller.contract.DronesControllerAPI;
import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.service.DronesDispatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DronesController implements DronesControllerAPI {
    private final DronesDispatcherService dispatcherService;

    @Override
    public DroneDto addDrone(DroneDto requestDto) {
        return dispatcherService.addDrone(requestDto);
    }

    @Override
    public List<DroneDto> listAvailableDrones() {
        Optional<List<DroneDto>> listOfAvailableDrones = dispatcherService.getAvailableDrones();
        return listOfAvailableDrones.orElse(Collections.emptyList());
    }

    @Override
    public Integer getDroneBatteryLevelBySN(String serialNumber) {
        var batteryLevel = dispatcherService.getDroneBatteryLevelBySN(serialNumber);
        if (batteryLevel != null) {
            return batteryLevel;
        } else {
            log.warn("getDroneBatteryLevelBySN request failed, drone with SN %s not found".formatted(serialNumber));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drone is not found");
        }
    }

    @Override
    public List<MedicationDto> getMedicationBySN(String serialNumber) {
        Optional<List<MedicationDto>> droneMedicationObBoard = dispatcherService.getDroneMedication(serialNumber);
        return droneMedicationObBoard.orElse(Collections.emptyList());
    }

    @Override
    public List<MedicationDto> addMedicationBySN(String serialNumber, MedicationDto requestDto) {
        return null;
    }

    @Override
    public void addImage(String serialNumber, String medicationId, byte[] image) {

    }

    @Override
    public byte[] getImage(String serialNumber, String medicationId) {
        return new byte[0];
    }
}
