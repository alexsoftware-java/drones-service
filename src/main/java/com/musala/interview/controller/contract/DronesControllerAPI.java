package com.musala.interview.controller.contract;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.validator.ValidDrone;
import com.musala.interview.validator.ValidMedication;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/drones")
@Validated
public interface DronesControllerAPI {
    @GetMapping
    List<DroneDto> listAvailableDrones();

    @GetMapping(value = "/{serialNumber}/battery")
    Integer getDroneBatteryLevelBySN( @PathVariable("serialNumber") String serialNumber);

    @PostMapping
    DroneDto addDrone(@ValidDrone @RequestBody DroneDto droneRequestDto);

    @GetMapping(value = "/{serialNumber}/medication")
    List<MedicationDto> getMedicationBySN(@PathVariable("serialNumber") String serialNumber);

    @PostMapping(value = "/{serialNumber}/medication")
    List<MedicationDto> addMedicationBySN(@PathVariable("serialNumber") String serialNumber,
                                          @ValidMedication @RequestBody MedicationDto medicationRequestDto);

    @PostMapping(value = "/{serialNumber}/medication/{medicationId}/image", consumes = MediaType.IMAGE_JPEG_VALUE)
    void addImage(@NotNull @PathVariable("medicationId") Long medicationId,
                  MultipartFile image);

    @GetMapping(value = "/{serialNumber}/medication/{medicationId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getImage(@NotNull @PathVariable("medicationId") Long medicationId);

}
