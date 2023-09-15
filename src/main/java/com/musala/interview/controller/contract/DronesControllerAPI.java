package com.musala.interview.controller.contract;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.validator.ValidDrone;
import com.musala.interview.validator.ValidMedication;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.musala.interview.utils.Constants.DRONE_MAX_SN_LENGTH;
import static com.musala.interview.utils.Constants.DRONE_MIN_SN_LENGTH;

@RequestMapping("/api/v1/drones")
@Validated
public interface DronesControllerAPI {
    @GetMapping
    List<DroneDto> listAvailableDrones();

    @GetMapping(value = "/{serialNumber}/battery")
    Integer getDroneBatteryLevelBySN(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber);

    @GetMapping(value = "/{serialNumber}/medication")
    List<MedicationDto> getMedicationBySN(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber);

    @PutMapping(value = "/{serialNumber}/medication")
    List<MedicationDto> addMedicationBySN(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber,
                                          @ValidMedication @RequestBody MedicationDto medicationRequestDto);

    @PostMapping(value = "/{serialNumber}/medication/{medicationId}/image", consumes = MediaType.IMAGE_JPEG_VALUE)
    void addImage(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber,
                  @PathVariable("{medicationId}") String medicationId,
                  byte[] image);

    @GetMapping(value = "/{serialNumber}/medication/{medicationId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getImage(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber,
                    @PathVariable("{medicationId}") String medicationId);

    @PostMapping
    void createDrone(@ValidDrone @RequestBody DroneDto droneRequestDto);
}
