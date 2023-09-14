package com.musala.interview.controller.contract;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.DroneRequestDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.dto.MedicationRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/drones")
public interface DronesControllerAPI {
    @GetMapping
    List<DroneDto> listAvailableDrones();

    @GetMapping(value = "/battery/{serialNumber}")
    Integer getDroneBatteryLevelBySN(@PathVariable("{serialNumber}") String serialNumber);

    @GetMapping(value = "/medication/{serialNumber}")
    List<MedicationDto> getMedicationBySN(@PathVariable("{serialNumber}") String serialNumber);

    @PutMapping(value = "/medication/{serialNumber}")
    List<MedicationDto> addMedicationBySN(@PathVariable("{serialNumber}") String serialNumber, @RequestBody MedicationRequestDto medicationRequestDto);

    @PostMapping
    void createDrone(@RequestBody DroneRequestDto droneRequestDto);
}
