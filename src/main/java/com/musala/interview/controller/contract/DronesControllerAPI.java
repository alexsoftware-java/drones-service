package com.musala.interview.controller.contract;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.MedicationDto;
import com.musala.interview.validator.ValidDrone;
import com.musala.interview.validator.ValidMedication;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @GetMapping(value = "/battery/{serialNumber}")
    Integer getDroneBatteryLevelBySN(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber);

    @GetMapping(value = "/medication/{serialNumber}")
    List<MedicationDto> getMedicationBySN(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber);

    @PutMapping(value = "/medication/{serialNumber}")
    List<MedicationDto> addMedicationBySN(@Min(DRONE_MIN_SN_LENGTH) @Max(DRONE_MAX_SN_LENGTH) @PathVariable("{serialNumber}") String serialNumber,
                                          @ValidMedication @RequestBody MedicationDto medicationRequestDto);

    @PostMapping
    void createDrone(@ValidDrone @RequestBody DroneDto droneRequestDto);
}
