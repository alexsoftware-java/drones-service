package com.musala.interview.controller;

import com.musala.interview.controller.contract.DronesControllerAPI;
import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.DroneRequestDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.dto.MedicationRequestDto;
import com.musala.interview.service.DronesDispatcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DronesController implements DronesControllerAPI {
    private final DronesDispatcherService dispatcherService;

    @Override
    public List<DroneDto> listAvailableDrones() {
       Optional<List<DroneDto>> listOfAvailableDrones = dispatcherService.getAvailableDrones();
       return listOfAvailableDrones.orElse(Collections.emptyList());
    }

    @Override
    public Integer getDroneBatteryLevelBySN(String serialNumber) {
        return null;
    }

    @Override
    public List<MedicationDto> getMedicationBySN(String serialNumber) {
        Optional<List<MedicationDto>> droneMedicationObBoard = dispatcherService.getDroneMedication(serialNumber);
        return droneMedicationObBoard.orElse(Collections.emptyList());
    }

    @Override
    public List<MedicationDto> addMedicationBySN(String serialNumber, MedicationRequestDto requestDto) {
        return null;
    }

    @Override
    public void createDrone(DroneRequestDto requestDto) {

    }
}
