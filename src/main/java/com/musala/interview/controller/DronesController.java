package com.musala.interview.controller;

import com.musala.interview.controller.contract.DronesControllerAPI;
import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.service.DroneService;
import com.musala.interview.service.MedicationService;
import com.musala.interview.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DronesController implements DronesControllerAPI {
    private final DroneService dispatcherService;
    private final MedicationService medicationService;
    private final ImageService imageService;

    @Override
    public DroneDto addDrone(DroneDto requestDto) {
        return dispatcherService.addDrone(requestDto);
    }

    @Override
    public List<DroneDto> listAvailableDrones() {
        return dispatcherService.getAvailableDrones();
    }

    @Override
    public Integer getDroneBatteryLevelBySN(String serialNumber) {
        return dispatcherService.getDroneBatteryLevelBySN(serialNumber);
    }

    @Override
    public List<MedicationDto> getMedicationBySN(String droneSerialNumber) {
        return medicationService.getDroneMedication(droneSerialNumber);
    }

    @Override
    public List<MedicationDto> addMedicationBySN(String droneSerialNumber, MedicationDto requestDto) {
        return medicationService.addMedication(droneSerialNumber, requestDto);
    }

    @Override
    public void addImage(Long medicationId, MultipartFile image) {
        imageService.addImage(medicationId, image);
    }

    @Override
    public byte[] getImage(Long medicationId) {
        return imageService.getImage(medicationId);
    }
}
